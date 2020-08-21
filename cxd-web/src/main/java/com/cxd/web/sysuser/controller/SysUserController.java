package com.cxd.web.sysuser.controller;

import com.cxd.tool.util.Pager;
import com.cxd.tool.vo.req.ReqWebParamsVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqAddUserVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqIdStatusVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqLoginVo;
import com.cxd.web.sysuser.pojo.vo.req.ReqUseFilterVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespUseListVo;
import com.cxd.web.sysuser.pojo.vo.resp.RespUserLoginVo;
import com.cxd.web.sysuser.process.SysUserProcess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

/**
 * @author MinWeikai
 * @date 2019/11/8 18:14
 */
@Api(tags = {"系统用户"})
@RequestMapping("approve/sysUser")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class SysUserController {

	@NonNull
	private SysUserProcess sysUserProcess;

	@ApiOperation("用户登陆")
	@PostMapping(value = "login")
	public RespUserLoginVo login(@RequestBody ReqLoginVo loginVo) {
		return sysUserProcess.login(loginVo);
	}

	@ApiOperation("退出登陆")
	@PostMapping(value = "logout")
	public void logout(@RequestBody ReqWebParamsVo paramsVo) {
		sysUserProcess.logout(paramsVo.getCommon().getUserId());
	}

	@ApiOperation("用户列表")
	@PostMapping(value = "list")
	@RequiresPermissions("sysUser:list")
	public Pager<RespUseListVo> list(@RequestBody ReqWebParamsVo<ReqUseFilterVo> paramsVo) {
		return sysUserProcess.listPage(paramsVo.getParam());
	}

	@ApiOperation("用户删除")
	@PostMapping(value = "delete")
	@RequiresPermissions("sysUser:delete")
	public void delete(@RequestBody ReqWebParamsVo<ReqIdStatusVo> paramsVo) {
		sysUserProcess.delete(paramsVo.getParam());
	}

	@ApiOperation("是否锁定用户")
	@PostMapping("luck")
	@RequiresPermissions("sysUser:luck")
	public void luck(@RequestBody ReqWebParamsVo<ReqIdStatusVo> paramsVo) {
		sysUserProcess.luck(paramsVo.getParam());
	}

	@ApiOperation("新增用户")
	@PostMapping(value = "save")
	@RequiresPermissions("sysUser:save")
	public void save(@RequestBody ReqWebParamsVo<ReqAddUserVo> paramsVo) {
		sysUserProcess.save(paramsVo.getParam());
	}
}
