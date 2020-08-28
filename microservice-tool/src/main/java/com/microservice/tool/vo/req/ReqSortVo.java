package com.microservice.tool.vo.req;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * @author MinWeikai
 * @date 2019/8/31 16:10
 */
@ApiModel("排序参数")
@Data
public class ReqSortVo {

	/**
	 * 需要排序的参数
	 */
	private String param;
	/**
	 * 正序-asc,倒序-desc
	 */
	private String sort;
}