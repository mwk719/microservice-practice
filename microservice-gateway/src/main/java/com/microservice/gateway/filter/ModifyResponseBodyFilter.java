package com.microservice.gateway.filter;

import cn.hutool.core.convert.Convert;
import com.microservice.gateway.params.IgnoreUri;
import com.microservice.tool.advice.ResponseBodyAdvice;
import feign.form.util.CharsetUtil;
import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.core.io.buffer.DefaultDataBufferFactory;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * 修改响应体过滤器
 *
 * @author MinWeikai
 * @date 2019-12-21 15:01:15
 */
@Slf4j
@Component
public class ModifyResponseBodyFilter implements GlobalFilter, Ordered {

	/**
	 * 忽略的请求路径
	 */
	@Autowired
	private IgnoreUri ignoreUri;

	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ServerHttpResponse originalResponse = exchange.getResponse();
		String nowPath = exchange.getRequest().getURI().getPath();
		DataBufferFactory bufferFactory = originalResponse.bufferFactory();
		ServerHttpResponseDecorator decoratedResponse = new ServerHttpResponseDecorator(originalResponse) {
			@Override
			public Mono<Void> writeWith(Publisher<? extends DataBuffer> body) {
				if (body instanceof Flux) {
					Flux<? extends DataBuffer> fluxBody = (Flux<? extends DataBuffer>) body;

					return super.writeWith(fluxBody.buffer().map(dataBuffers -> {

						DataBufferFactory dataBufferFactory = new DefaultDataBufferFactory();
						DataBuffer join = dataBufferFactory.join(dataBuffers);

						byte[] content = new byte[join.readableByteCount()];

						join.read(content);
						// 释放掉内存
						DataBufferUtils.release(join);
						//拦截到的返回体内容，可以随意去操作了
						String str = new String(content, CharsetUtil.UTF_8);
						str = ResponseBodyAdvice.modifyResponseBody(str, nowPath, ignoreUri.getIgnoreResultUris()).toString();
						//请求执行时间
						printExecutionTime(exchange);
						originalResponse.getHeaders().setContentLength(str.getBytes().length);
						return bufferFactory.wrap(str.getBytes());
					}));

				}
				// if body is not a flux. never got there.
				return super.writeWith(body);
			}
		};
		// replace response with decorator
		return chain.filter(exchange.mutate().response(decoratedResponse).build());

	}

	private void printExecutionTime(ServerWebExchange exchange) {
		long start = Convert.toLong(exchange.getAttributes().get("startTime"));
		log.info("[请求执行时间：{} 毫秒]", System.currentTimeMillis() - start);
	}

	@Override
	public int getOrder() {
		return -1;
	}
}