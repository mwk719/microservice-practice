package com.cxd.mobile.customer.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2019/11/4 10:59
 */
@ApiModel("客户登陆返回")
@Data
public class RespCustomerLoginVo implements Serializable {

	@ApiModelProperty(value = "客户ID")
	private Integer customerId;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;
	/**
	 * 锁定;1-锁定，不允许登录，0-未锁定
	 */
	@ApiModelProperty(value = "锁定;1-锁定，不允许登录，0-未锁定")
	private Integer luck;
	/**
	 * 昵称;饭店或餐厅名
	 */
	@ApiModelProperty(value = "昵称;饭店或餐厅名")
	private String nickname;

	/**
	 * 手机号
	 */
	@ApiModelProperty(value = "手机号")
	private String mobilePhone;

	/**
	 * 签名使用的
	 */
	@ApiModelProperty(value = "签名使用的")
	private String key;
}
