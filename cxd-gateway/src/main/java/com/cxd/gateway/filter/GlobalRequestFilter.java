package com.cxd.gateway.filter;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.lang.Assert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.json.JSONUtil;
import com.cxd.gateway.params.IgnoreUri;
import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.constant.URIPrefixEnum;
import com.cxd.tool.exception.DataException;
import com.cxd.tool.util.CommonUtil;
import com.cxd.tool.util.RedisSpringProxy;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;

/**
 * 全局请求过滤配置
 *
 * @author MinWeikai
 * @date 2019-12-24 11:39:33
 */
@Slf4j
@Component
public class GlobalRequestFilter implements GlobalFilter, Ordered, ErrorWebExceptionHandler {

	@Autowired
	private RedisSpringProxy redisSpringProxy;

	@Autowired
	private IgnoreUri ignoreUri;


	/**
	 * MessageReader
	 */
	private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

	/**
	 * MessageWriter
	 */
	private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

	/**
	 * ViewResolvers
	 */
	private List<ViewResolver> viewResolvers = Collections.emptyList();

	/**
	 * 存储处理异常后的信息
	 */
	private ThreadLocal<Map<String, Object>> exceptionHandlerResult = new ThreadLocal<>();

	private DataException dataException;

	/**
	 * 参考AbstractErrorWebExceptionHandler
	 */
	public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
		Assert.notNull(messageReaders, "'messageReaders' must not be null");
		this.messageReaders = messageReaders;
	}

	/**
	 * 参考AbstractErrorWebExceptionHandler
	 */
	public void setViewResolvers(List<ViewResolver> viewResolvers) {
		this.viewResolvers = viewResolvers;
	}

	/**
	 * 参考AbstractErrorWebExceptionHandler
	 */
	public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
		Assert.notNull(messageWriters, "'messageWriters' must not be null");
		this.messageWriters = messageWriters;
	}

	/**
	 * 拦截异常处理
	 *
	 * @param exchange
	 * @param ex
	 * @return
	 */
	@Override
	public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
		// 按照异常类型进行处理
		//封装响应体,此body可修改为自己的jsonBody
		Map<String, Object> result = new HashMap<>(2, 1);
		result.put("httpStatus", HttpStatus.OK);
		if (ObjectUtil.isNull(dataException)) {
			dataException = new DataException(ErrorCode.ERROR);
		}
		result.put("body", JSONUtil.toJsonStr(dataException));
		//错误记录
		log.error(printErrorLog(exchange, ex));
		//参考AbstractErrorWebExceptionHandler
		if (exchange.getResponse().isCommitted()) {
			return Mono.error(ex);
		}
		exceptionHandlerResult.set(result);
		ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
		return RouterFunctions.route(RequestPredicates.all(), this::renderErrorResponse).route(newRequest)
				.switchIfEmpty(Mono.error(ex))
				.flatMap((handler) -> handler.handle(newRequest))
				.flatMap((response) -> write(exchange, response));

	}

	private String printErrorLog(ServerWebExchange exchange, Throwable ex) {
		ServerHttpRequest request = exchange.getRequest();
		StringBuilder sb = new StringBuilder();
		sb.append("\n");
		sb.append("======>[全局异常处理]异常请求路径:");
		//请求路径
		sb.append(request.getPath());
		sb.append("\n");
		sb.append("======>[数据异常]：");
		sb.append(dataException.getErrorMsg());
		sb.append("\n");
		sb.append("======>[错误消息]：");
		sb.append(ExceptionUtil.stacktraceToString(ex, 1000));
		return sb.toString();
	}

	/**
	 * 参考DefaultErrorWebExceptionHandler
	 */
	protected Mono<ServerResponse> renderErrorResponse(ServerRequest request) {
		Map<String, Object> result = exceptionHandlerResult.get();
		return ServerResponse.status((HttpStatus) result.get("httpStatus"))
				.contentType(MediaType.APPLICATION_JSON)
				.body(BodyInserters.fromValue(result.get("body")));
	}

	/**
	 * 参考AbstractErrorWebExceptionHandler
	 */
	private Mono<? extends Void> write(ServerWebExchange exchange,
	                                   ServerResponse response) {
		exchange.getResponse().getHeaders()
				.setContentType(response.headers().getContentType());
		return response.writeTo(exchange, new ResponseContext());
	}

	/**
	 * 过滤请求
	 *
	 * @param exchange
	 * @param chain
	 * @return
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpRequest request = exchange.getRequest();
		String method = request.getMethodValue();
		String contentType = request.getHeaders().getFirst(HttpHeaders.CONTENT_TYPE);
		//开始时间
		exchange.getAttributes().put("startTime", System.currentTimeMillis());
		//请求来源
		request.mutate().header("source", URIPrefixEnum.EXTERNAL.getValue()).build();
		//上传文件不做校验
		if (StringUtils.isNotBlank(contentType) && contentType.contains(MediaType.MULTIPART_FORM_DATA_VALUE)) {
			// 普通键值对，增加参数
			return chain.filter(exchange);
		}

		if (HttpMethod.POST.matches(method)) {
			return DataBufferUtils.join(exchange.getRequest().getBody())
					.flatMap(dataBuffer -> {
						byte[] bytes = new byte[dataBuffer.readableByteCount()];
						dataBuffer.read(bytes);
						String bodyString = new String(bytes, StandardCharsets.UTF_8);
						logTrace(exchange, bodyString);
						exchange.getAttributes().put("POST_BODY", bodyString);
						DataBufferUtils.release(dataBuffer);
						Flux<DataBuffer> cachedFlux = Flux.defer(() -> {
							DataBuffer buffer = exchange.getResponse().bufferFactory()
									.wrap(bytes);
							return Mono.just(buffer);
						});

						ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(
								exchange.getRequest()) {
							@Override
							public Flux<DataBuffer> getBody() {
								return cachedFlux;
							}
						};
						return chain.filter(exchange.mutate().request(mutatedRequest)
								.build());
					});
		} else if (HttpMethod.GET.matches(method)) {
			logTrace(exchange, request.getQueryParams().toString());
		}
		return chain.filter(exchange);
	}

	/**
	 * 日志信息
	 *
	 * @param exchange
	 * @param param    请求参数
	 * @return
	 */
	public void logTrace(ServerWebExchange exchange, String param) {
		/*
		 * 是否已打印日志
		 * {@link GlobalRequestFilter#filter(ServerWebExchange, GatewayFilterChain)}
		 * 该方法在一次请求中会执行两次，
		 * 暂时未找到方法解决，所以在此判断是否已打印包含了参数校验
		 */
		Boolean logTrace = Convert.toBool(exchange.getAttributes().get("logTrace"));
		if(Optional.ofNullable(logTrace).orElse(false)){
			return;
		}
		exchange.getAttributes().put("logTrace", true);
		ServerHttpRequest serverHttpRequest = exchange.getRequest();
		String path = serverHttpRequest.getURI().getPath();
		String method = serverHttpRequest.getMethodValue();
		String headers = serverHttpRequest.getHeaders().entrySet()
				.stream()
				.map(entry -> "            " + entry.getKey() + ": [" + String.join(";", entry.getValue()) + "]")
				.collect(Collectors.joining("\n"));
		log.info("\n" + "----------------             ----------------             ---------------->>\n" +
						"HttpMethod : {}\n" +
						"Uri        : {}\n" +
						"Param      : {}\n" +
						"Headers    : \n" +
						"{}\n" +
						"\"<<----------------             ----------------             ----------------"
				, method, path, param, headers);
		//校验参数并返回
		this.dataException = CommonUtil.check(method, path, param, redisSpringProxy, ignoreUri.getIgnoreCheckUris());
		if (ObjectUtil.isNotNull(dataException)) {
			throw new RuntimeException();
		}
	}

	@Override
	public int getOrder() {
		return -1;
	}

	/**
	 * 参考AbstractErrorWebExceptionHandler
	 */
	private class ResponseContext implements ServerResponse.Context {

		@Override
		public List<HttpMessageWriter<?>> messageWriters() {
			return GlobalRequestFilter.this.messageWriters;
		}

		@Override
		public List<ViewResolver> viewResolvers() {
			return GlobalRequestFilter.this.viewResolvers;
		}
	}
}
