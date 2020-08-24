package com.cxd.tool.util;


import cn.hutool.core.util.ArrayUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.json.JSON;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Set;
import java.util.SortedMap;

/**
 * 签名算法
 *
 * @author MinWeikai
 * @date 2019/9/18 19:39
 */
@Slf4j
public class SignUtil {

	/**
	 * 签名校验
	 * 可对参数完整性进行校验
	 * @param json
	 * @param key
	 * @return
	 */
	public static boolean check(JSON json, String key) {
		JSON commonJson = json.getByPath("common", JSON.class);
		String sign = commonJson.getByPath("sign").toString();
		commonJson.putByPath("sign", key);
		String sysSign = SecureUtil.md5(json.toString());
		log.info("系统生成的sign：{}", sysSign);
		return sign.equals(sysSign);
	}


	/**
	 * 签名算法sign
	 *
	 * @param parameters
	 * @param key
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static String createSign(SortedMap<String, Object> parameters, String key) {
		StringBuilder sb = new StringBuilder();
		Set es = parameters.entrySet();// 所有参与传参的参数按照accsii排序（升序）
		for (Object e : es) {
			Map.Entry entry = (Map.Entry) e;
			String k = (String) entry.getKey();
			Object v = entry.getValue();
			if (null != v && !"".equals(v) && !"sign".equals(k)
					&& !"key".equals(k)) {
				sb.append(k).append("=").append(v).append("&");
			}
		}
		sb.append("key=").append(key);
		String sign = SecureUtil.md5(sb.toString())
				.toUpperCase();
		return sign;
	}

	/**
	 * 生成签名
	 *
	 * @param objects 多字段
	 * @return
	 */
	public static String createSign(Object... objects) {
		if (ArrayUtil.isEmpty(objects)) {
			return null;
		}
		StringBuilder sb = new StringBuilder();
		sb.append(System.currentTimeMillis());
		for (Object s : objects) {
			sb.append(s);
		}
		return SecureUtil.md5(sb.toString());
	}

	public static void main(String[] args) {
		System.out.println(createSign(1111, 2222, 222));
	}

}
