package com.microservice.web.sysuser.service;

import com.microservice.repository.sysuser.dao.repository.SysRoleRepository;
import com.microservice.repository.sysuser.dao.repository.SysUserRoleRepository;
import com.microservice.repository.sysuser.pojo.entity.SysRole;
import com.microservice.repository.sysuser.pojo.entity.SysUserRole;
import com.microservice.tool.constant.ErrorCode;
import com.microservice.tool.constant.YesOrNoInd;
import com.microservice.tool.exception.BusinessException;
import com.microservice.tool.util.AssertUtil;
import com.microservice.tool.util.CollectionUtil;
import com.microservice.tool.util.CommonBeanUtils;
import com.microservice.tool.util.ValidatorUtil;
import com.microservice.web.sysuser.pojo.bo.RoleBo;
import com.microservice.web.sysuser.pojo.vo.req.ReqAddRoleVo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinWeikai
 * @date 2019/11/11 11:09
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleService {

	@NonNull
	private SysUserRoleRepository sysUserRoleRepository;

	@NonNull
	private SysRoleRepository sysRoleRepository;

	public List<SysUserRole> findByUserId(Integer userId) {
		return sysUserRoleRepository.findByUserId(userId);
	}

	public RoleBo checkUserRole(Integer userId) {
		List<SysUserRole> roles = sysUserRoleRepository.findByUserId(userId);
		if (CollectionUtil.isEmpty(roles)) {
			BusinessException.throwMessage(ErrorCode.USER_NO_ROLE);
		}
		List<Integer> roleIds = roles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
		List<SysRole> roleList = sysRoleRepository.findByStatusAndRoleIdIn(YesOrNoInd.YES.getValue(), roleIds);
		return RoleBo.builder()
				.role(roleList.get(0))
				.roleIds(roleIds)
				.build();
	}

	public List<SysRole> findAll() {
		return sysRoleRepository.findAll();
	}

	public void save(ReqAddRoleVo param) {
		ValidatorUtil.validateNull(param, new String[]{"role", "description"});
		SysRole role = sysRoleRepository.findByRole(param.getRole());
		AssertUtil.isNotNull(role, ErrorCode.ROLE_REPEAT);
		SysRole sysRole = CommonBeanUtils.copyProperties(param, SysRole.class);
		sysRole.setStatus(YesOrNoInd.YES.getValue());
		sysRoleRepository.save(sysRole);
	}

	public List<SysRole> findByStatusAndRoleIdIn(Integer value, List<Integer> roleIds) {
		return sysRoleRepository.findByStatusAndRoleIdIn(value, roleIds);
	}

	public void deleteUserRoleByUserId(Integer userId) {
		sysUserRoleRepository.deleteByUserId(userId);
	}

	public void deleteByRoleId(Integer id) {
		sysRoleRepository.deleteByRoleId(id);
	}
}
