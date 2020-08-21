package com.cxd.tool.util;


import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.exception.BusinessException;
import org.springframework.util.CollectionUtils;

import java.util.Collection;

public class AssertUtil {

	public static void isNull(Object object, String message) {
		if (object != null) {
			BusinessException.throwMessage(message);
		}
	}
	public static void isNull(Object object, ErrorCode errorCode) {
		if (object != null) {
			BusinessException.throwMessage(errorCode);
		}
	}

	public static void isNotNull(Object object, ErrorCode errorCode) {
		if (object == null) {
			BusinessException.throwMessage(errorCode);
		}
	}

	public static void isNotNull(Collection<?> collection, ErrorCode errorCode) {
		if (CollectionUtils.isEmpty(collection)) {
			BusinessException.throwMessage(errorCode);
		}
	}

	/**
	 * 断言对象不为null，为null则抛出异常
	 */
	public static void isNotNull(Object object, String message) {
		if (object == null) {
			BusinessException.throwMessage(message);
		}
	}

	/**
	 * 断言多个对象不为null，为null则抛出异常
	 */
	public static void isParamNotNull(Object... objects) {
		isParamNotNull(ErrorCode.PARAM_NULL, objects);
	}

	public static void isParamNotNull(ErrorCode errorCode, Object... objects) {
		isParamNotNull(errorCode.getMsg(), objects);
	}

	/**
	 * 断言多个对象不为null，为null则抛出异常
	 */
	public static void isParamNotNull(String msg, Object... objects) {
		for (Object object : objects) {
			if (null == object || "".equals(object.toString().trim()) || "null".equals(object.toString().trim())) {
				BusinessException.throwMessage(msg);
			}
		}
	}

	/**
	 * 断言表达式为true，否则抛出异常
	 */
	public static void isTrue(boolean expression, String message) {
		if (!expression) {
			BusinessException.throwMessage(message);
		}
	}

	public static void isTrue(boolean expression, ErrorCode errorCode) {
		if (!expression) {
			BusinessException.throwMessage(errorCode);
		}
	}

	/**
	 * 断言表达式为true，否则抛出异常
	 */
	public static void isFalse(boolean expression, String message) {
		if (expression) {
			BusinessException.throwMessage(message);
		}
	}

	public static void isFalse(boolean expression, ErrorCode errorCode) {
		if (expression) {
			BusinessException.throwMessage(errorCode);
		}
	}

	public static void test(Object... object) {

	}

}
