package com.microservice.tool.constant;

/**
 * @author MinWeikai
 * @date 2019/9/17 11:40
 */
public enum RedisKeyEnum {

	/**
	 *  微服务项目实战key前缀
	 */
	CXD_KEY("microservice_key_"),
	/**
	 * 配置参数
	 */
	CONFIG("config_"),

	CUSTOMER("customer_"),
	USER("user_"),

	PERMISSION_LOG("permission_log_"),

	;

	private String value;


	RedisKeyEnum(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

	/**
	 * 获取 微服务项目实战key
	 *
	 * @return
	 */
	public String getCxdValue() {
		return RedisKeyEnum.CXD_KEY.value + value;
	}

	public String getCxdValue(Object object) {
		return RedisKeyEnum.CXD_KEY.value + value + object;
	}

	/**
	 * 获取 微服务项目实战配置key
	 *
	 * @return
	 */
	public String getCxdConfigValue() {
		return RedisKeyEnum.CXD_KEY.value + RedisKeyEnum.CONFIG.value + value;
	}

	public String getCxdConfigValue(Object object) {
		return RedisKeyEnum.CXD_KEY.value + RedisKeyEnum.CONFIG.value + value + object;
	}
}
