package com.cxd.web.common.config;

import cn.hutool.json.JSON;
import com.cxd.tool.util.CommonUtil;
import com.cxd.tool.util.StringUtils;
import com.cxd.web.sysuser.service.SysLogService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author MinWeikai
 * @date 2019-11-15 15:16:25
 */
@Slf4j
@Component
public class LogHandlerInterceptor extends HandlerInterceptorAdapter {

	@NonNull
	private SysLogService sysLogService;

	private static String JSON;

	public LogHandlerInterceptor(SysLogService sysLogService) {
		this.sysLogService = sysLogService;
	}

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
		//公共参数校验并输出
		JSON json = CommonUtil.check(request, handler);
		if (StringUtils.isBlank(json)) {
			return true;
		}
		assert json != null;
		JSON = json.toString();
		return true;
	}


	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
	                       Object handler, @Nullable ModelAndView modelAndView) {
		try {
			sysLogService.save(JSON);
		} catch (Exception e) {
			log.error("记录权限操作日志异常");
		}
	}


}