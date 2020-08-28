package com.microservice.web.sysuser.pojo.bo;

import com.microservice.repository.sysuser.pojo.entity.SysPermission;
import com.microservice.repository.sysuser.pojo.entity.SysRole;
import com.microservice.repository.sysuser.pojo.entity.SysUser;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/9 15:54
 */
@Data
@Builder
public class RolesAndPermissionsBo implements Serializable {

	private List<SysRole> roles;

	private List<SysPermission> permissions;

	private SysUser user;

}
