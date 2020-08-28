package com.microservice.web.sysuser.service;

import com.microservice.repository.sysuser.dao.repository.SysPermissionRepository;
import com.microservice.repository.sysuser.dao.repository.SysRolePermissionRepository;
import com.microservice.repository.sysuser.pojo.entity.SysPermission;
import com.microservice.repository.sysuser.pojo.entity.SysRolePermission;
import com.microservice.tool.constant.ErrorCode;
import com.microservice.tool.constant.ResourceTypeEnum;
import com.microservice.tool.exception.BusinessException;
import com.microservice.tool.util.CollectionUtil;
import com.microservice.tool.util.CommonBeanUtils;
import com.microservice.tool.util.StringUtils;
import com.microservice.web.sysuser.pojo.vo.resp.RespPermissionMenuVo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author MinWeikai
 * @date 2019/11/11 11:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class PermissionService {

	@NonNull
	private SysRolePermissionRepository sysRolePermissionRepository;

	@NonNull
	private SysPermissionRepository sysPermissionRepository;

	public List<RespPermissionMenuVo> getMenuPermission(List<Integer> roleIds) {
		List<SysPermission> permissions = this.getSysPermission(roleIds, ResourceTypeEnum.MENU.getValue(), 0);
		if (CollectionUtil.isEmpty(permissions)) {
			BusinessException.throwMessage(ErrorCode.ROLE_NO_PERMISSION);
		}
		List<Integer> permissionIds = permissions.stream().map(SysPermission::getPermissionId).collect(Collectors.toList());
		List<SysPermission> permissionList = sysPermissionRepository
				.findByParentIdInAndResourceTypeOrderBySortDesc(permissionIds, ResourceTypeEnum.MENU.getValue());

		Map<Integer, List<RespPermissionMenuVo>> permissionMap = CommonBeanUtils
				.copyListProperties(permissionList, RespPermissionMenuVo.class)
				.stream()
				.collect(Collectors.groupingBy(RespPermissionMenuVo::getParentId));

		List<RespPermissionMenuVo> permissionMenuVos = new ArrayList<>();
		RespPermissionMenuVo permissionMenuVo;
		for (SysPermission p : permissions) {
			permissionMenuVo = CommonBeanUtils.copyProperties(p, RespPermissionMenuVo.class);
			List<RespPermissionMenuVo> sysPermissions = permissionMap.get(p.getPermissionId());
			permissionMenuVo.setPermissionMenuVos(sysPermissions);
			permissionMenuVos.add(permissionMenuVo);
		}
		return permissionMenuVos;
	}

	private List<SysPermission> getSysPermission(List<Integer> roleIds, String resourceType, Integer parentId) {
		List<SysRolePermission> rolePermissions = sysRolePermissionRepository.findByRoleIdIn(roleIds);
		List<Integer> permissionIds = rolePermissions.stream().map(SysRolePermission::getPermissionId).collect(Collectors.toList());
		List<SysPermission> permissions;
		if (StringUtils.isBlank(resourceType)) {
			permissions = sysPermissionRepository.findByPermissionIdInOrderBySortDesc(permissionIds);
		} else {
			permissions = sysPermissionRepository.findByPermissionIdInAndParentIdAndResourceTypeOrderBySortDesc(permissionIds, parentId, resourceType);
		}
		return permissions;
	}

	public List<SysPermission> getSysPermission(List<Integer> roleIds) {
		return this.getSysPermission(roleIds, null, null);
	}

}
