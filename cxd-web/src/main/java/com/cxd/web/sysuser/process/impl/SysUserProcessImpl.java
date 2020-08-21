package com.cxd.web.sysuser.process.impl;

import com.cxd.repository.sysuser.pojo.entity.SysUser;
import com.cxd.tool.util.Pager;
import com.cxd.tool.util.ValidatorUtil;
import com.cxd.web.sysuser.pojo.vo.req.ReqAddUserVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqIdStatusVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqLoginVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqUseFilterVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespUseListVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespUserLoginVo;
import com.cxd.web.sysuser.process.SysUserProcess;
import com.cxd.web.sysuser.service.SysUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
		SysUser user = sysUserService.findByUserId(vo.getId());
		sysUserService.luck(user, vo.getLuck());
	}

	@Override
	public void save(ReqAddUserVo param) {
		//添加用户
		sysUserService.save(param);
	}
}
