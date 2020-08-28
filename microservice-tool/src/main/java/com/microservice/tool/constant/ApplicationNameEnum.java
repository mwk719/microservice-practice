package com.microservice.tool.constant;

/**
 * 运行程序
 *
 * @author MinWeikai
 * @date 2019-11-11 11:31:21
 */
public enum ApplicationNameEnum {

	/**
	 *
	 */
	MOBILE("microservice-mobile"),
	WEB("microservice-web"),

	;

	private String value;


	ApplicationNameEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
