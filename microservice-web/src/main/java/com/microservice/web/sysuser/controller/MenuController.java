package com.microservice.web.sysuser.controller;

import com.microservice.tool.vo.req.ReqWebParamsVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespPermissionMenuVo;
import com.microservice.web.sysuser.process.PermissionProcess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/18 10:56
 */
@Api(tags = {"权限菜单"})
@RequestMapping("common/menu")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class MenuController {

	@NonNull
	private PermissionProcess permissionProcess;

	@ApiOperation("用户菜单")
	@PostMapping("list")
	public List<RespPermissionMenuVo> list(@RequestBody ReqWebParamsVo paramsVo) {
		return permissionProcess.list(paramsVo.getCommon().getUserId());
	}
}
