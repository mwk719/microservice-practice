package com.microservice.tool.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MinWeikai
 * @date 2019/11/13 11:11
 */
@ApiModel("链接")
@Data
public class RespUrlVo {

	@ApiModelProperty(value = "链接")
	private String url;

	public RespUrlVo() {
	}

	public RespUrlVo(String url) {
		this.url = url;
	}
}
