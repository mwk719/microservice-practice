package com.cxd.web.sysuser.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2019/11/4 10:59
 */
@ApiModel("用户登陆返回")
@Data
public class RespUserLoginVo implements Serializable {

	@ApiModelProperty(value = "用户ID")
	private Integer userId;
	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 昵称;饭店或餐厅名
	 */
	@ApiModelProperty(value = "昵称;饭店或餐厅名")
	private String nickname;

	@ApiModelProperty(value = "角色")
	private String role;
}
