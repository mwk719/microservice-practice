package com.microservice.tool.constant;

/**
 * 接口服务uri前缀
 *
 * @author MinWeikai
 * @date 2019/12/13 17:43
 */
public enum URIPrefixEnum {

	/**
	 * 内部服务调用前缀
	 */
	INTERIOR("interior"),
	/**
	 * 外部服务调用标志
	 */
	EXTERNAL("external"),
	;

	private String value;


	URIPrefixEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
