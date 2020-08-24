package com.cxd.tool.util;


import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.exception.BusinessException;
import com.cxd.tool.vo.req.ResultVo;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2020-01-21 15:42:10
 */
@Data
public class ResponseResult<T> implements Serializable {
	private static final long serialVersionUID = 1L;
	/**
	 * 状态码
	 */
	private String code;

	/**
	 * 错误描述
	 */
	private String msg;

	private T data;

	public ResponseResult() {
		this.code = ErrorCode.SUCCESS.getCode();
		this.msg = ErrorCode.SUCCESS.getMsg();
	}

	public ResponseResult(T data) {
		this.code = ErrorCode.SUCCESS.getCode();
		this.msg = ErrorCode.SUCCESS.getMsg();
		this.data = data;
	}

	public ResponseResult(BusinessException exception) {
		this.code = exception.getCode();
		this.msg = exception.getMsg();
	}

	public ResponseResult(ErrorCode errorCode) {
		this.code = errorCode.getCode();
		this.msg = errorCode.getMsg();
	}

	public ResponseResult(String errorCode, String errorMsg) {
		this.code = errorCode;
		this.msg = errorMsg;
	}

	public ResponseResult(String errorCode, String errorMsg, T data) {
		this.code = errorCode;
		this.msg = errorMsg;
		this.data = data;
	}

	public T getData() {
		return this.data;
	}

	public ResponseResult SUCCESS(T data) {
		this.code = ErrorCode.SUCCESS.getCode();
		this.data = data;
		return this;
	}

	public ResponseResult setResultVo(T data) {
		return new ResponseResult().SUCCESS(new ResultVo<>(data));
	}
}
