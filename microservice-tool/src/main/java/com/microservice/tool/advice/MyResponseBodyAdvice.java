package com.microservice.tool.advice;

import com.microservice.tool.constant.URIPrefixEnum;
import com.microservice.tool.util.RequestHelper;
import com.microservice.tool.util.ResponseResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 修饰返回体
 *
 * @author MinWeikai
 * @date 2019/6/24 10:56
 */
@Slf4j
public class MyResponseBodyAdvice implements ResponseBodyAdvice {

	@Override
	public Object beforeBodyWrite(Object body, MethodParameter returnType, MediaType selectedContentType,
	                              Class selectedConverterType, ServerHttpRequest rq, ServerHttpResponse response) {
		HttpServletRequest request = RequestHelper.getRequest();

		String source = request.getHeader("source");
		//内部服务请求则不包装
		if (URIPrefixEnum.INTERIOR.getValue().equals(source)) {
			return body;
		}

		if (body == null) {
			return new ResponseResult<>("");
		}
		//匹配ResponseResult
		if (body instanceof ResponseResult) {
			return body;
		}
		//匹配集合
		if (body instanceof Collection) {
			return new ResponseResult<>().setResultVo(body);
		}

		return new ResponseResult<>().SUCCESS(body);
	}

	@Override
	public boolean supports(MethodParameter returnType, Class converterType) {
		return true;
	}
}