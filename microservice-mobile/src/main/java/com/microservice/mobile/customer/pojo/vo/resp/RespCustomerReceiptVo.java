package com.microservice.mobile.customer.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2019/11/4 11:24
 */
@ApiModel("客户收货信息返回")
@Data
public class RespCustomerReceiptVo implements Serializable {

	/**
	 * 客户ID
	 */
	@ApiModelProperty(value = "客户ID")
	private Integer customerId;
	/**
	 * 收货人
	 */
	@ApiModelProperty(value = "收货人")
	private String consignee;
	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String mobilePhone;
	/**
	 * 收货地址
	 */
	@ApiModelProperty(value = "收货地址")
	private String address;
}
