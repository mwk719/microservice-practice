package com.microservice.tool.util;

import com.microservice.tool.constant.ErrorCode;
import com.microservice.tool.exception.BusinessException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.spec.AlgorithmParameterSpec;

/**
 * @author MinWeikai
 * @date 2019/11/4 18:02
 */
@Slf4j
public class AesUtil {

	/**
	 * 小程序登陆解码
	 *
	 * @param key
	 * @param iv
	 * @param encData
	 * @return
	 * @throws Exception
	 */
	public static String decrypt(byte[] key, byte[] iv, byte[] encData) throws Exception {
		AlgorithmParameterSpec ivSpec = new IvParameterSpec(iv);
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
		SecretKeySpec keySpec = new SecretKeySpec(key, "AES");
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);
		// 解析解密后的字符串
		return new String(cipher.doFinal(encData), "UTF-8");
	}

	/**
	 * 小程序登陆解码
	 *
	 * @param key
	 * @param iv
	 * @param encData
	 * @return
	 */
	public static String decrypt(String key, String iv, String encData) {
		byte[] encrypData = Base64.decodeBase64(encData);
		byte[] ivData = Base64.decodeBase64(iv);
		byte[] sessionKey = Base64.decodeBase64(key);
		try {
			return decrypt(sessionKey, ivData, encrypData);
		} catch (Exception e) {
			log.error("---小程序信息解码失败---", e);
			throw new BusinessException(ErrorCode.ERROR);
		}
	}

}
