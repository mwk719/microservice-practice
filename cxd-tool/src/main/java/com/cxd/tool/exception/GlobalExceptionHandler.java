package com.cxd.tool.exception;

import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.constant.URIPrefixEnum;
import com.cxd.tool.util.RequestHelper;
import com.cxd.tool.util.ResponseResult;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;


/**
 * 所有异常拦截
 *
 * @author 闵渭凯
 * <p>
 * 2018年11月4日
 */
@Slf4j
@Data
public class GlobalExceptionHandler {

	private String applicationName;

	private String activeProfile;

	/**
	 * 统一拦截所有未定义异常报错
	 *
	 * @return
	 */
	@ExceptionHandler(value = Exception.class)
	ResponseResult allExceptionHandler(Exception e) throws Exception {
		log.error("{} {}", RequestHelper.getRequestURI(), e.getMessage(), e);
		checkRequest();
		return new ResponseResult(ErrorCode.ERROR.getCode(), ErrorCode.ERROR.getMsg());
	}

	/**
	 * 处理所有业务异常
	 *
	 * @param e
	 * @return
	 */
	@ExceptionHandler(BusinessException.class)
	ResponseResult<BusinessException> handleBusinessException(BusinessException e) throws Exception {
		log.error("{} {}", RequestHelper.getRequestURI(), e.getMsg());
		checkRequest();
		return new ResponseResult<>(e);
	}

	private void checkRequest() throws Exception {
		HttpServletRequest request = RequestHelper.getRequest();
		assert request != null;
		String source = request.getHeader("source");
		//内部服务请求则不包装
		if (URIPrefixEnum.INTERIOR.getValue().equals(source)) {
			throw new Exception("内部自调用错误");
		}
	}

}