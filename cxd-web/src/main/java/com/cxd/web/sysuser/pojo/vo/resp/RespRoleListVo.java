package com.cxd.web.sysuser.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2019/11/14 11:28
 */
@ApiModel("角色列表返回")
@Data
public class RespRoleListVo implements Serializable {

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

	private String statusStr;

	/**
	 * 描述
	 */
	@ApiModelProperty(value = "描述")
	private String description;

	public String getStatusStr() {
		if (1 == status) {
			return statusStr = "有效";
		}
		return statusStr = "无效";
	}
}
