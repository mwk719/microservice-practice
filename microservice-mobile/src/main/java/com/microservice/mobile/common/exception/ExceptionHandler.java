package com.microservice.mobile.common.exception;

import com.microservice.mobile.common.config.SpringContextUtil;
import com.microservice.tool.exception.GlobalExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MinWeikai
 * @date 2019/9/11 15:10
 */
@RestControllerAdvice
public class ExceptionHandler extends GlobalExceptionHandler {

	{
		super.setApplicationName(SpringContextUtil.getApplicationName());
		super.setActiveProfile(SpringContextUtil.getActiveProfile());
	}
}
