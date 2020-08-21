package com.cxd.web.sysuser.pojo.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author MinWeikai
 * @date 2019/10/10 14:40
 */
@ApiModel("用户登陆")
@Data
public class ReqLoginVo implements Serializable {

	@ApiModelProperty(value = "用户名", example = "minweikai")
	private String username;

	@ApiModelProperty(value = "密码", example = "123456")
	private String password;
}
