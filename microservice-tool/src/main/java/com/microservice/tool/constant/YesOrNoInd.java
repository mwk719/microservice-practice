package com.microservice.tool.constant;

/**
 * 是否标识。
 * <p>
 * 全局所有是否字段统一枚举值定义。
 *
 * @author liaohj
 */
public enum YesOrNoInd {

	/**
	 * 是
	 */
	YES(1, "是"),

	/**
	 * 否
	 */
	NO(0, "否"),

	;
	private Integer value;

	private String desc;


	private YesOrNoInd(Integer value, String desc) {
		this.value = value;
		this.desc = desc;
	}

	public Integer getValue() {
		return value;
	}

	public String getDesc() {
		return desc;
	}

	public boolean eq(String value) {
		return this.value.equals(value);
	}

	/**
	 * 根据描述，找到枚举值
	 *
	 * @param desc
	 * @return value
	 */
	public static Integer getValue(String desc) {

		for (YesOrNoInd code : values()) {
			if (code.getDesc().equals(desc)) {
				return code.getValue();
			}
		}
		return null;
	}

	/**
	 * 根据值获取描述
	 *
	 * @param value
	 * @return
	 */
	public static String getDesc(Integer value) {
		if (value == null) {
			return "";
		}
		for (YesOrNoInd code : values()) {
			if (code.getValue().equals(value)) {
				return code.getDesc();
			}
		}
		return "";
	}
}
