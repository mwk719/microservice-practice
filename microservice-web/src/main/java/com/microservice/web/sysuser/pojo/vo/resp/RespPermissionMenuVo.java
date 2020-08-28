package com.microservice.web.sysuser.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/11 14:16
 */
@ApiModel("权限菜单列表")
@Data
public class RespPermissionMenuVo implements Serializable {

	@ApiModelProperty(value = "权限ID")
	private Integer permissionId;
	/**
	 * 权限名
	 */
	@ApiModelProperty(value = "权限名")
	private String name;
	/**
	 * 资源路径
	 */
	@ApiModelProperty(value = "资源路径")
	private String url;

	/**
	 * 父编号
	 */
	private Integer parentId;

	@ApiModelProperty(value = "子项权限菜单")
	private List<RespPermissionMenuVo> permissionMenuVos;
}
