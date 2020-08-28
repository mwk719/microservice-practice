package com.microservice.tool.constant;

/**
 * @author MinWeikai
 * @date 2019/8/26 12:03
 */
public enum ErrorCode {
	//
	/**
	 *
	 */
	SUCCESS("0", "成功"),
	ERROR("1", "服务端异常"),
	ERROR_SIGN("2", "签名异常"),
	ERROR_V_CODE("3", "验证码错误"),
	NO_PERMISSION("4", "没有权限访问"),
	SEND_ERROR("5", "发送失败"),
	CODE_EXPIRED("6", "验证码已过期"),

	/**
	 * 用户状态2000-2500
	 */
	USER_NOT_EXIST("2000", "该用户不存在"),
	PASSWORD_ERROR("2013", "密码错误"),
	LOGIN_EXPIRED("2014", "登陆已过期，请重新登录"),
	NUM_REPEAT("2015", "账号重复，请联系客服"),
	OTHER_LOGIN("2016", "您已在其他地方登陆"),
	ACCOUNT_LOCKED("2017", "账号已被锁定，请联系客服"),
	LOGIN_ERROR("2018", "登陆异常"),
	UNAUTHENTICATED("2019", "身份验证失败，请重新登录"),
	USER_NO_ROLE("2020", "该用户未分配角色，请联系管理员"),
	ROLE_NO_PERMISSION("2021", "该角色未分配权限，请联系管理员"),
	ROLE_REPEAT("2022", "角色重复"),


	/**
	 * 异常
	 * 7000-8000
	 */
	COMMON_PARAM_NULL("7002", "公共参数不能为空"),
	LACK_COMMON_PARAM("7003", "缺少公共参数"),
	CONVERSION_ERROR("7004", "对象赋值转化异常"),
	PARAM_NULL("7005", "参数不能为空"),
	OBJECT_NULL("7008", "校验的对象不允许为空!"),
	PHONE_NULL("7010", "手机号不能为空"),
	PHONE_FORMAT_ERROR("7011", "手机号格式错误"),
	REQUEST_ERROR("7021", "请求方式错误，请检查"),

	;
	private String code;
	private String msg;

	ErrorCode(String code, String msg) {
		this.code = code;
		this.msg = msg;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}
}
