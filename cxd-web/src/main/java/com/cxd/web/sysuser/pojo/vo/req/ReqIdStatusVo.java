package com.cxd.web.sysuser.pojo.vo.req;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @author MinWeikai
 * @date 2019/11/18 11:16
 */
@ApiModel("根据id修改状态模型")
@Data
public class ReqIdStatusVo {

	@ApiModelProperty("需要进行操作的对象id")
	private Integer id;

	@ApiModelProperty("1-禁用/锁定，0-启用/未锁定，根据情况可不传")
	private Integer luck;
}
