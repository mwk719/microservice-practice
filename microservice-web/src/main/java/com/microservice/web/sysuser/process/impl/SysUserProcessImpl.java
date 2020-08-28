package com.microservice.web.sysuser.process.impl;

import com.microservice.repository.sysuser.pojo.entity.SysUser;
import com.microservice.tool.util.Pager;
import com.microservice.tool.util.ValidatorUtil;
import com.microservice.web.sysuser.pojo.vo.req.ReqAddUserVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqIdStatusVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqLoginVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqUseFilterVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespUseListVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespUserLoginVo;
import com.microservice.web.sysuser.process.SysUserProcess;
import com.microservice.web.sysuser.service.SysUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author MinWeikai
 * @date 2019/11/9 10:52
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserProcessImpl implements SysUserProcess {

	@NonNull
	private SysUserService sysUserService;

	@Override
	public RespUserLoginVo login(ReqLoginVo login) {
		SysUser user = sysUserService.createSubject(login);
		return sysUserService.loginSuccess(user);
	}

	@Override
	public void delete(ReqIdStatusVo vo) {
		sysUserService.deleteByUserId(vo.getId());
	}

	@Override
	public void logout(Integer userId) {
		sysUserService.removeSubject();
	}

	@Override
	public Pager<RespUseListVo> listPage(ReqUseFilterVo param) {
		return sysUserService.listPage(param);
	}

	@Override
	public void luck(ReqIdStatusVo vo) {
		ValidatorUtil.validateNull(vo, new String[]{"id", "luck"});
		Optional<SysUser> opt = sysUserService.findById(vo.getId());
		opt.ifPresent(sysUser -> sysUserService.luck(sysUser, vo.getLuck()));
	}

	@Override
	public void save(ReqAddUserVo param) {
		//添加用户
		sysUserService.save(param);
	}
}
