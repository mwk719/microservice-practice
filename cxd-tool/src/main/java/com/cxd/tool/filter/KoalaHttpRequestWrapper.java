package com.cxd.tool.filter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StreamUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 保存流
 */
@Slf4j
public class KoalaHttpRequestWrapper extends HttpServletRequestWrapper {

	/**
	 * 用于将流保存下来
	 */
	private byte[] requestBody = null;

	public KoalaHttpRequestWrapper(HttpServletRequest request) {
		super(request);
		try {
			requestBody = StreamUtils.copyToByteArray(request.getInputStream());
		} catch (IOException e) {
			log.error(e.getMessage(),"保存流出错");
		}
	}

	@Override
	public ServletInputStream getInputStream() {
		if (requestBody == null) {
			requestBody = new byte[0];
		}
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(requestBody);
		return new ServletInputStream() {

			@Override
			public int read() {
				return byteArrayInputStream.read();
			}

			@Override
			public void setReadListener(ReadListener listener) {
				// do nothing
			}

			@Override
			public boolean isReady() {
				return false;
			}

			@Override
			public boolean isFinished() {
				return false;
			}
		};
	}

	@Override//对外提供读取流的方法
	public BufferedReader getReader() {
		return new BufferedReader(new InputStreamReader(getInputStream()));
	}

}