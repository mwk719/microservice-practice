package com.microservice.tool.constant;

/**
 * @author MinWeikai
 * @date 2019-11-11 11:31:21
 */
public enum ResourceTypeEnum {

	/**
	 * 菜单
	 */
	MENU("menu"),
	/**
	 * 按钮
	 */
	BUTTON("button"),

	;

	private String value;


	ResourceTypeEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}
}
