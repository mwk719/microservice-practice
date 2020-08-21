package com.cxd.scheduled.common.advice;

import com.cxd.scheduled.common.config.SpringContextUtil;
import com.cxd.tool.exception.GlobalExceptionHandler;
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
