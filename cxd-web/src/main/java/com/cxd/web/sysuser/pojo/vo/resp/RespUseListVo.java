package com.cxd.web.sysuser.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @author MinWeikai
 * @date 2019/11/14 12:12
 */
@ApiModel("用户列表返回")
@Data
public class RespUseListVo implements Serializable {

	private Integer userId;

	/**
	 * 用户名
	 */
	@ApiModelProperty(value = "用户名")
	private String username;

	/**
	 * 管理员昵称
	 */
	@ApiModelProperty(value = "管理员昵称")
	private String nickname;

	@ApiModelProperty(value = "角色")
	private String description;

	/**
	 * 锁定;1-锁定，不允许登录，0-未锁定
	 */
	@ApiModelProperty(value = "锁定;1-禁用，0-启用")
	private Integer luck;

	private String luckStr;

	@ApiModelProperty(value = "创建时间")
	private Date createTime;

	public String getLuckStr() {
		if (1 == luck) {
			return luckStr = "禁用";
		}
		return luckStr = "启用";
	}
}
