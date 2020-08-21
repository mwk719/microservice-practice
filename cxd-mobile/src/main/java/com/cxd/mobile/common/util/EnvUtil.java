package com.cxd.mobile.common.util;

import com.cxd.mobile.common.config.SpringContextUtil;
import com.cxd.tool.constant.EnvEnum;

/**
 * 运行环境判断
 *
 * @author MinWeikai
 * @date 2019/11/13 14:45
 */
public class EnvUtil {

	/**
	 * 是否是测试环境，包含测试与开发
	 *
	 * @return
	 */
	public static boolean isDebug() {
		String active = SpringContextUtil.getActiveProfile();
		return /*EnvEnum.TEST.getValue().equals(active) ||*/
				EnvEnum.DEV.getValue().equals(active);
	}

}
