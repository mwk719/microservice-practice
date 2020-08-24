package com.cxd.tool.advice;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.json.JSON;
import cn.hutool.json.JSONUtil;
import com.cxd.tool.util.ResponseResult;
import com.cxd.tool.util.Tools;
import lombok.extern.slf4j.Slf4j;

import java.util.Collection;
import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;

/**
 * @author MinWeikai
 * @date 2019/12/23 15:09
 */
@Slf4j
public class ResponseBodyAdvice {

	/**
	 * 包装返回参数
	 *
	 * @param body        参数
	 * @param nowPath     访问路径
	 * @param ignorePaths 忽略路径
	 * @return
	 */
	public static Object modifyResponseBody(Object body, String nowPath, List<String> ignorePaths) {
		if (CollectionUtil.isNotEmpty(ignorePaths) && Tools.contains(ignorePaths, nowPath)) {
			return body;
		}

		JSON object = JSONUtil.parse(body);
		ResponseResult result;
		if (object == null) {
			result = new ResponseResult<>("");
		} else if (object instanceof ResponseResult) {
			//匹配ResponseResult
			return body;
		} else if (object instanceof Collection) {
			//匹配集合
			result = new ResponseResult<>().setResultVo(object);
		} else if ("1".equals(object.getByPath("code"))) {
			return body;
		} else if (null != object.getByPath("status", Integer.class)
				&& SC_OK != object.getByPath("status", Integer.class)) {
			//gateway请求的服务端出错
			throw new RuntimeException(object.toString());
		} else {
			result = new ResponseResult<>().SUCCESS(object);
		}
		return JSONUtil.parseObj(result);
	}


}
