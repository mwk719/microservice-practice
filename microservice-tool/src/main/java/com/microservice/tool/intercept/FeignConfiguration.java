package com.microservice.tool.intercept;

import com.microservice.tool.constant.URIPrefixEnum;
import feign.RequestInterceptor;
import feign.RequestTemplate;

/**
 * @author minweikai
 * @date 2019/2/27 16:14
 * Feign调用的时候添加请求头Token
 */
public class FeignConfiguration implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		//内部服务请求
		requestTemplate.header("source", URIPrefixEnum.INTERIOR.getValue());
	}
}