package com.cxd.tool.util;

import com.alibaba.fastjson.JSONObject;
import io.netty.util.ReferenceCountUtil;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 从HttpServletRequest中获取请求参数
 *
 * @author MinWeikai
 * @date 2019/7/5 10:58
 */
public class GetRequestJsonUtils {
	public static JSONObject getRequestJsonObject(HttpServletRequest request) throws IOException {
		String json = getRequestJsonString(request);
		return JSONObject.parseObject(json);
	}

	/***
	 * 获取 request 中 json 字符串的内容
	 *
	 * @param request
	 * @return : <code>byte[]</code>
	 * @throws IOException
	 */
	public static String getRequestJsonString(HttpServletRequest request)
			throws IOException {
		return getRequestPostStr(request);
	}

	/**
	 * 描述:获取 post 请求的 byte[] 数组
	 * <pre>
	 * 举例：
	 * </pre>
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static byte[] getRequestPostBytes(HttpServletRequest request)
			throws IOException {
		int contentLength = request.getContentLength();
		if (contentLength < 0) {
			return null;
		}
		byte buffer[] = new byte[contentLength];
		for (int i = 0; i < contentLength; ) {

			int readlen = request.getInputStream().read(buffer, i,
					contentLength - i);
			if (readlen == -1) {
				break;
			}
			i += readlen;
		}
		ReferenceCountUtil.release(buffer);
		return buffer;
	}

	/**
	 * 描述:获取 post 请求内容
	 * <pre>
	 * 举例：
	 * </pre>
	 *
	 * @param request
	 * @return
	 * @throws IOException
	 */
	public static String getRequestPostStr(HttpServletRequest request)
			throws IOException {
		byte[] buffer = getRequestPostBytes(request);
		String charEncoding = request.getCharacterEncoding();
		if (charEncoding == null) {
			charEncoding = "UTF-8";
		}
		ReferenceCountUtil.release(buffer);
		return new String(buffer, charEncoding);
	}


}