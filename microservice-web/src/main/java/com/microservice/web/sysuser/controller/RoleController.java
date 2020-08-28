package com.microservice.web.sysuser.controller;

import com.microservice.tool.vo.req.ReqWebParamsVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqAddRoleVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqIdStatusVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespRoleListVo;
import com.microservice.web.sysuser.process.RoleProcess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/14 11:18
 */
@Api(tags = {"角色权限"})
@RequestMapping("approve/role")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class RoleController {

	@NonNull
	private RoleProcess roleProcess;

	@ApiOperation("角色列表")
	@PostMapping(value = "list")
	@RequiresPermissions("role:list")
	public List<RespRoleListVo> list(@RequestBody ReqWebParamsVo paramsVo) {
		return roleProcess.list();
	}

	@ApiOperation("添加角色")
	@PostMapping(value = "save")
	@RequiresPermissions("role:save")
	public void save(@RequestBody ReqWebParamsVo<ReqAddRoleVo> paramsVo) {
		roleProcess.save(paramsVo.getParam());
	}

	@ApiOperation("删除角色")
	@PostMapping(value = "delete")
	@RequiresPermissions("role:delete")
	public void delete(@RequestBody ReqWebParamsVo<ReqIdStatusVo> paramsVo) {
		roleProcess.delete(paramsVo.getParam());
	}
}
