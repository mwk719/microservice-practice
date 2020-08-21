package com.cxd.tool.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 请求参数
 *
 * @author MinWeikai
 * @date 2019/6/22 18:45
 */
@ApiModel("请求参数")
@Data
public class ReqWebParamsVo<T> {

	/**
	 * 公共请求vo
	 */
	@ApiModelProperty(value = "公共请求vo")
	private ReqWebCommonVo common;

	/**
	 * 自定义vo
	 */
	@ApiModelProperty(value = "自定义vo")
	private T param;

	public ReqWebParamsVo(ReqWebCommonVo common) {
		this.common = common;
	}

	public ReqWebParamsVo() {
	}

}
