package com.microservice.web.sysuser.pojo.vo.req;

import com.microservice.tool.util.Pager;
import com.microservice.web.sysuser.pojo.vo.resp.RespUseListVo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MinWeikai
 * @date 2019/11/14 12:04
 */
@ApiModel("用户过滤")
@Data
public class ReqUseFilterVo {

	@ApiModelProperty(value = "用户名", example = "13333333333")
	private String username;

	@ApiModelProperty(value = "角色中名")
	private String description;

	private Pager<RespUseListVo> page;

	public ReqUseFilterVo() {
	}

	public ReqUseFilterVo(Pager page) {
		this.page = page;
	}
}
