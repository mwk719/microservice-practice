package com.cxd.web.sysuser.pojo.vo.resp;

import io.swagger.annotations.ApiModel;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/4 10:59
 */
@ApiModel("用户登陆权限菜单")
@Data
@Builder
public class RespLoginMenuVo implements Serializable {

	private RespUserLoginVo userVo;

	private List<RespPermissionMenuVo> permissionMenuVos;

}
