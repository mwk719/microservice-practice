package com.cxd.web.common.config;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import com.cxd.repository.sysuser.pojo.entity.SysPermission;
import com.cxd.repository.sysuser.pojo.entity.SysRole;
import com.cxd.repository.sysuser.pojo.entity.SysUser;
import com.cxd.tool.constant.ErrorCode;
import com.cxd.tool.constant.YesOrNoInd;
import com.cxd.tool.util.CollectionUtil;
import com.cxd.tool.util.StringUtils;
import com.cxd.web.sysuser.pojo.bo.RolesAndPermissionsBo;
import com.cxd.web.sysuser.service.SysUserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author MinWeikai
 * @date 2019-11-09 10:50:19
 */
@Slf4j
@RequiredArgsConstructor
public class SysUserRealm extends AuthorizingRealm {

	@NonNull
	private SysUserService sysUserService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
		RolesAndPermissionsBo rolesAndPermissionsBo = Convert.convert(RolesAndPermissionsBo.class, SecurityUtils.getSubject().getPrincipal());
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		//获取角色列表、权限列表
		List<String> roles = rolesAndPermissionsBo.getRoles()
				.stream().map(SysRole::getRole).collect(Collectors.toList());
		List<String> permissions = rolesAndPermissionsBo
				.getPermissions().stream().map(SysPermission::getPermission).collect(Collectors.toList());
		authorizationInfo.addRoles(roles);
		authorizationInfo.addStringPermissions(permissions);
		return authorizationInfo;
	}

	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
		String userName = (String) authenticationToken.getPrincipal();
		String userPwd = new String((char[]) authenticationToken.getCredentials());

		SysUser user = sysUserService.findByUsername(userName);
		//校验用户
		RolesAndPermissionsBo rolesAndPermissionsBo = this.checkUser(userName, userPwd, user);
		return new SimpleAuthenticationInfo(rolesAndPermissionsBo, user.getPassword(), getName());
	}

	private RolesAndPermissionsBo checkUser(String userName, String userPwd, SysUser user) {
		if (StringUtils.isBlank(userName, userPwd)) {
			throw new AccountException(ErrorCode.PARAM_NULL.getMsg());
		}
		if (ObjectUtil.isNull(user)) {
			throw new AccountException(ErrorCode.USER_NOT_EXIST.getMsg());
		}
		if (!userPwd.equals(user.getPassword())) {
			throw new AccountException(ErrorCode.PASSWORD_ERROR.getMsg());
		}
		//是否锁定
		if (YesOrNoInd.YES.getValue().equals(user.getLuck())) {
			throw new AccountException(ErrorCode.ACCOUNT_LOCKED.getMsg());
		}
		//获取角色列表、权限列表
		RolesAndPermissionsBo rolesAndPermissionsBo = sysUserService.getRolesAndPermissions(user.getUserId());
		rolesAndPermissionsBo.setUser(user);
		if (CollectionUtil.isEmpty(rolesAndPermissionsBo.getRoles())) {
			throw new AccountException(ErrorCode.USER_NO_ROLE.getMsg());
		}
		if (CollectionUtil.isEmpty(rolesAndPermissionsBo.getPermissions())) {
			throw new AccountException(ErrorCode.ROLE_NO_PERMISSION.getMsg());
		}
		return rolesAndPermissionsBo;
	}


}