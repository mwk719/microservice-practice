package com.cxd.tool.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 公共请求参数
 *
 * @author MinWeikai
 * @date 2019/6/2B 18:28
 */
@EqualsAndHashCode(callSuper = true)
@ApiModel("公共请求参数")
@Data
public class ReqCommonVo extends SignVo {

	/**
	 * 手机型号
	 */
	@ApiModelProperty(value = "手机型号")
	private String phoneModel;

	/**
	 * 设备编号
	 */
	@ApiModelProperty(value = "设备编号")
	private String machineNumber;

	/**
	 * app型号
	 */
	@ApiModelProperty(value = "app型号")
	private String appVersion;

	@ApiModelProperty(value = "客户id", example = "1")
	private Integer customerId;

	public ReqCommonVo() {
	}

	public ReqCommonVo(Integer customerId) {
		this.customerId = customerId;
	}

	public ReqCommonVo(String phoneModel, String machineNumber, String appVersion, Integer customerId, String sign) {
		this.phoneModel = phoneModel;
		this.machineNumber = machineNumber;
		this.appVersion = appVersion;
		this.customerId = customerId;
		super.setSign(sign);
	}
}
