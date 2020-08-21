package com.cxd.tool.util;


import cn.hutool.core.collection.CollectionUtil;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2020-08-21 12:01:57
 */
public class Tools {

	/**
	 * 检验模糊字符中是否包含精确字符
	 *
	 * @param precises 精确字符数组
	 * @param blurry   模糊字符
	 * @return
	 */
	public static boolean contains(List<String> precises, String blurry) {
		if (CollectionUtil.isNotEmpty(precises)) {
			for (String precise : precises) {
				if (blurry.contains(precise)) {
					return true;
				}
			}
		}
		return false;
	}

}
