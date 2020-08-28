package com.microservice.tool.exception;


import com.microservice.tool.constant.ErrorCode;
import lombok.Data;
import org.apache.poi.ss.formula.functions.T;

import java.io.Serializable;

@Data
public class DataException implements Serializable {
	/**
	 * 错误代码
	 */
	private String code;

	/**
	 * 错误描述
	 */
	private String msg;

	private T data;

	public DataException() {
	}
	public DataException(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public DataException(ErrorCode error) {
		this.code = error.getCode();
		this.msg = error.getMsg();
	}
}
