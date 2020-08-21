package com.cxd.web.sysuser.process.impl;

import com.cxd.repository.sysuser.pojo.entity.SysRole;
import com.cxd.tool.util.CommonBeanUtils;
import com.cxd.web.sysuser.pojo.vo.req.ReqAddRoleVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqIdStatusVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespRoleListVo;
import com.cxd.web.sysuser.process.RoleProcess;
import com.cxd.web.sysuser.service.RoleService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/14 11:23
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class RoleProcessImpl implements RoleProcess {

	@NonNull
	private RoleService roleService;

	@Override
	public List<RespRoleListVo> list() {
		List<SysRole> roles = roleService.findAll();
		return CommonBeanUtils.copyListProperties(roles, RespRoleListVo.class);
	}

	@Override
	public void save(ReqAddRoleVo param) {
		roleService.save(param);
	}

	@Override
	public void delete(ReqIdStatusVo param) {
		roleService.deleteByRoleId(param.getId());
	}
}
