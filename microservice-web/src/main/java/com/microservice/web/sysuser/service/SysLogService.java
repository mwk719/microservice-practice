package com.microservice.web.sysuser.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.microservice.repository.sysuser.dao.repository.SysLogRepository;
import com.microservice.repository.sysuser.pojo.entity.SysLog;
import com.microservice.repository.sysuser.pojo.entity.SysPermission;
import com.microservice.repository.sysuser.pojo.entity.SysRole;
import com.microservice.repository.sysuser.pojo.entity.SysUser;
import com.microservice.tool.constant.ErrorCode;
import com.microservice.tool.constant.RedisKeyEnum;
import com.microservice.tool.constant.ResourceTypeEnum;
import com.microservice.tool.util.ArrayUtils;
import com.microservice.tool.util.AssertUtil;
import com.microservice.tool.util.RequestHelper;
import com.microservice.tool.util.StringUtils;
import com.microservice.web.common.util.RedisSpringProxy;
import com.microservice.web.sysuser.pojo.bo.RolesAndPermissionsBo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/15 16:11
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysLogService {

	@NonNull
	private SysLogRepository sysLogRepository;

	@NonNull
	private RedisSpringProxy redisSpringProxy;

	public void save(String json) {
		RolesAndPermissionsBo rolesAndPermissionsBo = Convert.convert(RolesAndPermissionsBo.class, SecurityUtils.getSubject().getPrincipal());
		List<SysPermission> permissions = rolesAndPermissionsBo.getPermissions();
		String uri = RequestHelper.getRequestURI();
		String operateName = this.getOperateName(uri, permissions);
		//非敏感权限不记录日志
		if (StringUtils.isBlank(operateName)) {
			return;
		}
		SysUser user = rolesAndPermissionsBo.getUser();
		SysRole role = rolesAndPermissionsBo.getRoles().get(0);
		SysLog sysLog = new SysLog();
		sysLog.setIp(RequestHelper.getClientIP());
		sysLog.setParams(json);
		sysLog.setUri(uri);
		sysLog.setNickname(user.getNickname());
		sysLog.setUserId(user.getUserId());
		sysLog.setUsername(user.getUsername());
		sysLog.setOperateName(operateName);
		sysLog.setRoleName(role.getDescription());
		sysLogRepository.save(sysLog);
	}

	private String getOperateName(String uri, List<SysPermission> permissions) {
		List<String> ps = ArrayUtils.getArray("新增", "删除", "锁定");
		for (SysPermission permission : permissions) {
			if (permission.getResourceType().equals(ResourceTypeEnum.BUTTON.getValue()) &&
					this.checkUrl(uri, permission.getUrl()) &&
					this.checkPermissionName(permission.getName(), ps)) {
				return permission.getName();
			}
		}
		return null;
	}

	public void savePermissionLog(List<Integer> permissionIds) {
		AssertUtil.isNotNull(permissionIds, ErrorCode.PARAM_NULL);
		redisSpringProxy.save(RedisKeyEnum.PERMISSION_LOG.getCxdConfigValue(), permissionIds);
	}

	/**
	 * 匹配权限路径
	 *
	 * @param uri
	 * @param url
	 * @return
	 */
	private boolean checkUrl(String uri, String url) {
		String prefix = "/approve/";
		uri = StrUtil.removePrefix(uri, prefix).replace("/", "").toUpperCase();
		return uri.equals(url.toUpperCase());
	}

	/**
	 * 匹配需要记录的权限名
	 *
	 * @param permissionName
	 * @param ps
	 * @return
	 */
	private boolean checkPermissionName(String permissionName, List<String> ps) {
		for (String p : ps) {
			if (permissionName.contains(p)) {
				return true;
			}
		}
		return false;
	}
}
