package com.microservice.web.common.config;

import com.microservice.web.sysuser.service.SysLogService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

/**
 * 请求与返回参数处理
 *
 * @author MinWeikai
 * @date 2019/6/24 10:57
 */
@Configuration
@RequiredArgsConstructor
public class WebConfig extends com.microservice.tool.intercept.WebConfig {

	@NonNull
	private SysLogService sysLogService;

	@Override
	public void addInterceptors(InterceptorRegistry registry) {

		//业务拦截处理
		registry.addInterceptor(new LogHandlerInterceptor(sysLogService))
				.addPathPatterns("/approve/**")
				.excludePathPatterns("/approve/sysUser/login")
				.excludePathPatterns("/data/**")
				.excludePathPatterns("/test/**")
				.excludePathPatterns("/swagger-ui.html/**");
	}

	@Override
	public void addCorsMappings(CorsRegistry registry) {
		registry.addMapping("/**")
				.allowedOrigins("*")
				.allowedMethods("*")
				.allowedHeaders("*")
				//设置是否允许跨域传cookie
				.allowCredentials(true)
				//设置缓存时间，减少重复响应
				.maxAge(3600);
	}


}