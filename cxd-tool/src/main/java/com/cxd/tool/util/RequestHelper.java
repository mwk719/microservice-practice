package com.cxd.tool.util;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class RequestHelper {
	public static final String CURRENT_USER = "CURRENT_AUTHORIZATION_USER_";

	public RequestHelper() {
	}

	public static HttpServletRequest getRequest() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes == null ? null : attributes.getRequest();
	}

	public static HttpServletResponse getResponse() {
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		return attributes == null ? null : attributes.getResponse();
	}

	public static HttpSession getSession() {
		return getRequest().getSession();
	}

	public static boolean isAjaxRequest() {
		HttpServletRequest request = getRequest();
		return request.getHeader("accept") != null && request.getHeader("accept").indexOf("application/json") > -1 || request.getHeader("X-Requested-With") != null && request.getHeader("X-Requested-With").indexOf("XMLHttpRequest") > -1;
	}

	public static boolean isRefererRequest() {
		HttpServletRequest request = getRequest();
		if (request.getMethod().equals("POST")) {
			return false;
		} else {
			return request.getHeader("Referer") == null;
		}
	}

	public static boolean isIEbrowser() {
		HttpServletRequest request = getRequest();
		String userAgent = request.getHeader("user-agent");
		System.out.println("userAgent===" + userAgent);
		return userAgent.contains("MSIE") || userAgent.contains("Trident") || userAgent.contains("Edge");
	}

	public static String getClientIP() {
		HttpServletRequest request = getRequest();
		String ip = request.getHeader("x-forwarded-for");
		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("PX-Real-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}

		if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}

		return ip.contains(",") ? ip.split(",")[0] : ip;
	}

	public static String getAuthorization() {
		String Authorization = getRequest().getHeader("Authorization");
		return "undefined".equalsIgnoreCase(Authorization) ? null : Authorization;
	}

	public static String getAccessToken() {
		return getAuthorization();
	}

	public static String getRequestURL() {
		return getRequest().getRequestURL().toString();
	}

	public static String getRequestURI() {
		return getRequest().getRequestURI();
	}

}