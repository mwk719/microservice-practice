package com.cxd.web.sysuser.pojo.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2019/11/14 11:28
 */
@ApiModel("添加角色")
@Data
public class ReqAddRoleVo implements Serializable {

	/**
	 * 角色名
	 */
	@ApiModelProperty(value = "角色名")
	private String role;
	/**
	 * 状态;1-有效，0-无效
	 */
	@ApiModelProperty(value = "状态;1-有效，0-无效")
	private Integer status;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	public ReqAddRoleVo() {
	}

	public ReqAddRoleVo(String role, String description) {
		this.role = role;
		this.description = description;
	}
}
