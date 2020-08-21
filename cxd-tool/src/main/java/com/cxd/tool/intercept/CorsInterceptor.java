package com.cxd.tool.intercept;

import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author MinWeikai
 * @date 2019/7/5 17:11
 */
public class CorsInterceptor extends HandlerInterceptorAdapter {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		request.setAttribute("_REQUEST_STARTTIME_", System.currentTimeMillis());

		// 此处配置的是允许任意域名跨域请求，可根据需求指定
		response.setHeader("Access-Control-Allow-Origin", "*");
		//允许cookie
		response.setHeader("Access-Control-Allow-Credentials", "true");
		response.setHeader("Access-Control-Allow-Methods", "GET, HEAD, POST, PUT, PATCH, DELETE, OPTIONS");
		//预检请求间隔时间
		response.setHeader("Access-Control-Max-Age", "86400");
		response.setHeader("Access-Control-Allow-Headers", "*");

		// 如果是OPTIONS则结束请求
		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
			response.setStatus(HttpStatus.NO_CONTENT.value());
			return false;
		}
		return true;
	}
}
