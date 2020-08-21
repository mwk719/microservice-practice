package com.cxd.tool.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MinWeikai
 * @date 2019/10/11 12:10
 */
@ApiModel("签名参数")
@Data
public class SignVo {

	/**
	 * 签名
	 */
	@ApiModelProperty(value = "签名", example = "24d501bd50f6d6f8e6776b545b4ade5b")
	private String sign;
}
