package com.cxd.tool.util;

import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.cxd.tool.constant.ApplicationNameEnum;
import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.constant.RedisKeyEnum;
import com.cxd.tool.exception.BusinessException;
import com.cxd.tool.exception.DataException;
import com.cxd.tool.filter.KoalaHttpRequestWrapper;
import com.cxd.tool.vo.req.ReqCommonVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.web.servlet.resource.DefaultServletHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * 公共参数操作
 *
 * @author MinWeikai
 * @date 2019/9/12 18:27
 */
@Slf4j
public class CommonUtil {

	public static JSON check(HttpServletRequest request, Object handler, String id) throws IOException {

		//判断请求方式,GET请求暂不验证
		if (HttpMethod.GET.matches(request.getMethod())) {
			return null;
		}

		//对请求流进行复制
		KoalaHttpRequestWrapper requestWrapper = new KoalaHttpRequestWrapper(request);

		String params = GetRequestJsonUtils.getRequestJsonString(requestWrapper);

		//获取请求当时的uri
		if (handler instanceof DefaultServletHttpRequestHandler) {
			return null;
		}
		//格式化json
		JSON json;
		try {
			json = JSONUtil.parse(params);
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new BusinessException(ErrorCode.COMMON_PARAM_NULL);
		}
		return json;
	}

	public static JSON check(HttpServletRequest request, Object handler) throws IOException {
		return check(request, handler, "userId");
	}

	/**
	 * 校验参数
	 *
	 * @param method
	 * @param path            get请求不做校验
	 * @param param
	 * @param ignoreCheckUris
	 * @return null 没有异常
	 */
	public static DataException check(String method, String path, String param, RedisSpringProxy redisSpringProxy
			, List<String> ignoreCheckUris) {
		//检验路径
		if (Tools.contains(ignoreCheckUris, path)) {
			return null;
		}

		if (HttpMethod.GET.matches(method)) {
			return null;
		}
		if (StringUtils.isBlank(param)) {
			return new DataException(ErrorCode.LACK_COMMON_PARAM);
		}
		//判断请求来源
		if (path.contains(ApplicationNameEnum.MOBILE.getValue())) {
			//格式化json
			JSON json;
			ReqCommonVo commonVo;
			try {
				json = JSONUtil.parse(param);
				//json转化为vo
				commonVo = json.getByPath("common", ReqCommonVo.class);
				if (StringUtils.isBlank(commonVo.getCustomerId(), commonVo.getAppVersion()
						, commonVo.getMachineNumber(), commonVo.getPhoneModel(), commonVo.getSign())) {
					return new DataException(ErrorCode.COMMON_PARAM_NULL);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				return new DataException(ErrorCode.LACK_COMMON_PARAM);
			}
			//校验登录key
			String key = String.valueOf(redisSpringProxy.read(RedisKeyEnum.CUSTOMER.getCxdValue(commonVo.getCustomerId())));
			if (StringUtils.isBlank(key)) {
				return new DataException(ErrorCode.LOGIN_EXPIRED);
			}

			//签名校验
			if (!SignUtil.check(json, key)) {
				return new DataException(ErrorCode.ERROR_SIGN);
			}
			//成功返回
			return null;
		}
		if (path.contains(ApplicationNameEnum.WEB.getValue())) {
			//格式化json
			JSON json;
			JSON commonJson;
			try {
				json = JSONUtil.parse(param);
				commonJson = json.getByPath("common", JSON.class);
				Object object = commonJson.getByPath("userId");
				if (StringUtils.isBlank(object)) {
					return new DataException(ErrorCode.COMMON_PARAM_NULL);
				}
			} catch (Exception e) {
				log.error(e.getMessage());
				return new DataException(ErrorCode.LACK_COMMON_PARAM);
			}
			//成功返回
			return null;
		}
		return new DataException(ErrorCode.REQUEST_ERROR);
	}
}
