package com.cxd.web.sysuser.pojo.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2019/11/19 15:15
 */
@ApiModel("新增用户")
@Data
@Builder
public class ReqAddUserVo implements Serializable {

	private Integer userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 密码
	 */
	@ApiModelProperty(value = "密码")
	private String password;

	/**
	 * 管理员昵称
	 */
	@ApiModelProperty(value = "管理员昵称")
	private String nickname;

	@ApiModelProperty(value = "角色id")
	private Integer roleId;

}
