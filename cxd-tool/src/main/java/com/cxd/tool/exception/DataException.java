package com.cxd.tool.exception;


import com.cxd.tool.constant.ErrorCode;

import java.io.Serializable;


public class DataException implements Serializable {
	/**
	 * 错误代码
	 */
	private String errorCode = "1";

	/**
	 * 错误描述
	 */
	private String errorMsg;

	public DataException() {
	}

	public DataException(String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public DataException(String errorCode, String errorMsg) {
		this.errorCode = errorCode;
		this.errorMsg = errorMsg;
	}

	public DataException(Throwable cause, String errorMsg) {
		this.errorMsg = errorMsg;
	}

	public DataException(ErrorCode errorCode) {
		this.errorCode = errorCode.getCode();
		this.errorMsg = errorCode.getMsg();
	}

	public String getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(String errorCode) {
		this.errorCode = errorCode;
	}

	public String getErrorMsg() {
		return errorMsg;
	}

	public void setErrorMsg(String errorMsg) {
		this.errorMsg = errorMsg;
	}
}
