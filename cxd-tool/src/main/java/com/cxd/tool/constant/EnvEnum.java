package com.cxd.tool.constant;

/**
 * 运行环境
 *
 * @author MinWeikai
 * @date 2019-11-11 11:31:21
 */
public enum EnvEnum {

	/**
	 *
	 */
	DEV("dev"),
	TEST("test"),
	PROD("prod"),

	;

	private String value;


	EnvEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
