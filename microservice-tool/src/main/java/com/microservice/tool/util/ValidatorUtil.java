package com.microservice.tool.util;

import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ReflectUtil;
import com.microservice.tool.constant.ErrorCode;
import com.microservice.tool.exception.BusinessException;
import com.microservice.tool.vo.req.ReqCommonVo;

/**
 * 校验数据
 *
 * @author MinWeikai
 * @date 2019/9/15 10:04
 */
public class ValidatorUtil {

	/**
	 * 是否是手机号
	 *
	 * @param phone
	 */
	public static void isMobile(String phone) {
		if (StringUtils.isBlank(phone)) {
			throw new BusinessException(ErrorCode.PHONE_NULL);
		}
		if (!Validator.isMobile(phone)) {
			throw new BusinessException(ErrorCode.PHONE_FORMAT_ERROR);
		}
	}

	/**
	 * 字段为空校验
	 *
	 * @param bean
	 * @param propertyNames
	 */
	public static void validateNull(Object bean, String[] propertyNames) {
		StringBuffer errorMessage = new StringBuffer();
		if (bean == null) {
			throw new BusinessException(ErrorCode.OBJECT_NULL);
		} else if (propertyNames != null && propertyNames.length != 0) {
			String[] var6 = propertyNames;
			int var5 = propertyNames.length;

			for (int var4 = 0; var4 < var5; ++var4) {
				String name = var6[var4];
				Object value = ReflectUtil.getFieldValue(bean, name);
				if (StringUtils.isBlank(value)) {
					errorMessage.append("[").append(name).append("]不能为空;");
				}
			}

			if (errorMessage.length() > 0) {
				throw new BusinessException(ErrorCode.PARAM_NULL.getCode(), errorMessage.toString());
			}
		}
	}

	public static void main(String[] args) {
		//isMobile("13222222222");
		ReqCommonVo commonVo = new ReqCommonVo();
		validateNull(commonVo, new String[]{"sign"});

	}
}
