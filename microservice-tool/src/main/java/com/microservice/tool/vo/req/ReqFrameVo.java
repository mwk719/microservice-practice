package com.microservice.tool.vo.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author MinWeikai
 * @date 2019/9/2 12:08
 */
@ApiModel("范围查询")
@Data
public class ReqFrameVo {

	/**
	 * 要查询的参数
	 */
	private String param;

	/**
	 * 开始
	 */
	private String start;

	/**
	 * 结束
	 */
	private String end;
}
