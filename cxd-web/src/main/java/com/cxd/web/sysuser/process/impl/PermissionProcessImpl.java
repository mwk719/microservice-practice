package com.cxd.web.sysuser.process.impl;

import com.cxd.repository.sysuser.pojo.entity.SysUserRole;
import com.cxd.web.sysuser.pojo.vo.resp.RespPermissionMenuVo;
import com.cxd.web.sysuser.process.PermissionProcess;
import com.cxd.web.sysuser.service.PermissionService;
import com.cxd.web.sysuser.service.RoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinWeikai
 * @date 2019/11/18 11:00
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionProcessImpl implements PermissionProcess {

	@NonNull
	private PermissionService permissionService;

	@NonNull
	private RoleService roleService;

	@Override
	public List<RespPermissionMenuVo> list(Integer userId) {
		List<SysUserRole> roles = roleService.findByUserId(userId);
		return permissionService.getMenuPermission(roles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList()));
	}
}
