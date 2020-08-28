package com.microservice.web.common.exception;

import com.microservice.tool.constant.ErrorCode;
import com.microservice.tool.exception.GlobalExceptionHandler;
import com.microservice.tool.util.RequestHelper;
import com.microservice.tool.util.ResponseResult;
import com.microservice.web.common.config.SpringContextUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.UnauthenticatedException;
import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author MinWeikai
 * @date 2019/9/11 15:10
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandler extends GlobalExceptionHandler {

	{
		super.setApplicationName(SpringContextUtil.getApplicationName());
		super.setActiveProfile(SpringContextUtil.getActiveProfile());
	}

	@org.springframework.web.bind.annotation.ExceptionHandler(UnauthorizedException.class)
	ResponseResult handleUnauthorizedException(UnauthorizedException e) {
		log.error("{} {}", RequestHelper.getRequestURI(), e.getMessage());
		return new ResponseResult<>(ErrorCode.NO_PERMISSION);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({UnauthenticatedException.class})
	ResponseResult handleUnauthenticatedException(UnauthenticatedException e) {
		log.error("{} {}", RequestHelper.getRequestURI(), e.getMessage());
		return new ResponseResult<>(ErrorCode.UNAUTHENTICATED);
	}

	@org.springframework.web.bind.annotation.ExceptionHandler({HttpRequestMethodNotSupportedException.class})
	ResponseResult handleHttpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
		final String loginUri = "/approve/sysUser/login";
		String uri = RequestHelper.getRequestURI();
		//如果是登陆则返回
		if (loginUri.equals(uri)) {
			return new ResponseResult<>(ErrorCode.UNAUTHENTICATED);
		}
		log.error("{} {}", uri, e.getMessage());
		return new ResponseResult(ErrorCode.REQUEST_ERROR);
	}
}
