package com.microservice.tool.constant;

/**
 * @author MinWeikai
 * @date 2019-11-11 17:03:18
 */
public enum LuckEnum {
	/**
	 * 1-锁定，0-未锁定
	 */
	NO_LUCK(0, "未锁定"),
	LUCKED(1, "锁定"),

	;

	private Integer key;
	private String value;

	public static String getValue(Integer key) {
		if (key == null) {
			return "";
		}
		for (LuckEnum value : LuckEnum.values()) {
			if (value.getKey().equals(key)) {
				return value.getValue();
			}
		}
		return "";
	}

	public static Integer getKey(String value) {
		if (value == null) {
			return null;
		}
		for (LuckEnum e : LuckEnum.values()) {
			if (e.getValue().equals(value)) {
				return e.getKey();
			}
		}
		return null;
	}

	LuckEnum(Integer key, String value) {
		this.key = key;
		this.value = value;
	}

	public Integer getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

}
