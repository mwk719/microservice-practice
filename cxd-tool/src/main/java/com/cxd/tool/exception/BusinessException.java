package com.cxd.tool.exception;


import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.util.StringUtils;

/**
 * @author minweikai
 * @date 2019-02-19 16:45
 **/
public class BusinessException extends RuntimeException {
	/**
	 * 错误代码
	 */
	private String code = "1";

	/**
	 * 错误描述
	 */
	private String msg;

	public BusinessException() {
	}

	public BusinessException(String msg) {
		super(msg);
		this.msg = msg;
	}

	public BusinessException(String code, String msg) {
		super(msg);
		this.code = code;
		this.msg = msg;
	}

	public BusinessException(Throwable cause, String msg) {
		super(msg, cause);
		this.msg = msg;
	}

	public BusinessException(ErrorCode code) {
		this.code = code.getCode();
		this.msg = code.getMsg();
	}

	public static void throwMessage(String message) {
		throw new BusinessException(message);
	}

	public static void throwMessage(Object object, ErrorCode errorCode) {
		if (StringUtils.isBlank(object)) {
			throw new BusinessException(errorCode);
		}
	}

	public static void throwMessage(ErrorCode errorCode) {
		throw new BusinessException(errorCode);
	}

	public static void throwMessageWithCode(String errorCode, String message) {
		throw new BusinessException(errorCode, message);
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
