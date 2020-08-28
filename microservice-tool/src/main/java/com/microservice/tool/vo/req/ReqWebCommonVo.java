package com.microservice.tool.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * web公共请求参数
 *
 * @author MinWeikai
 * @date 2019/6/2B 18:28
 */
@ApiModel("公共请求参数")
@Data
public class ReqWebCommonVo implements Serializable {

	@ApiModelProperty(value = "用户id", example = "1")
	private Integer userId;

	public ReqWebCommonVo() {
	}

	public ReqWebCommonVo(Integer userId) {
		this.userId = userId;
	}
}
