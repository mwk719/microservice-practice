package com.microservice.web.sysuser.service;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.crypto.SecureUtil;
import com.microservice.repository.sysuser.dao.repository.SysUserRepository;
import com.microservice.repository.sysuser.pojo.entity.SysPermission;
import com.microservice.repository.sysuser.pojo.entity.SysRole;
import com.microservice.repository.sysuser.pojo.entity.SysUser;
import com.microservice.repository.sysuser.pojo.entity.SysUserRole;
import com.microservice.tool.constant.ErrorCode;
import com.microservice.tool.constant.LuckEnum;
import com.microservice.tool.constant.RedisKeyEnum;
import com.microservice.tool.constant.YesOrNoInd;
import com.microservice.tool.exception.BusinessException;
import com.microservice.tool.util.*;
import com.microservice.web.common.util.RedisSpringProxy;
import com.microservice.web.sysuser.dao.queryfactory.UserQueryFactory;
import com.microservice.web.sysuser.pojo.bo.RoleBo;
import com.microservice.web.sysuser.pojo.bo.RolesAndPermissionsBo;
import com.microservice.web.sysuser.pojo.vo.req.ReqAddUserVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqLoginVo;
import com.microservice.web.sysuser.pojo.vo.req.ReqUseFilterVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespUseListVo;
import com.microservice.web.sysuser.pojo.vo.resp.RespUserLoginVo;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @author MinWeikai
 * @date 2019/11/9 10:53
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SysUserService {

	@NonNull
	private SysUserRepository sysUserRepository;

	@NonNull
	private RoleService roleService;

	@NonNull
	private PermissionService permissionService;

	@NonNull
	private RedisSpringProxy redisSpringProxy;

	@NonNull
	private UserQueryFactory userQueryFactory;

	public SysUser findByUsername(String userName) {
		return sysUserRepository.findByUsername(userName);
	}

	public RespUserLoginVo loginSuccess(SysUser user) {
		//校验用户是否有角色
		RoleBo roleBo = roleService.checkUserRole(user.getUserId());
		//查询权限列表
		RespUserLoginVo loginVo = CommonBeanUtils.copyProperties(user, RespUserLoginVo.class);
		loginVo.setRole(roleBo.getRole().getDescription());
		return loginVo;
	}

	/**
	 * 登陆成功生成key
	 *
	 * @param user
	 * @return
	 */
	public String createKey(SysUser user) {
		String key;
		String redisKey = RedisKeyEnum.USER.getValue() + user.getUserId();
		Object object = redisSpringProxy.read(redisKey);
		if (StringUtils.isBlank(object)) {
			key = SignUtil.createSign(user.getUsername(), user.getPassword());
			//有效期30天
			redisSpringProxy.saveEx(redisKey, 60 * 60 * 24 * 30, key);
		}
		return Convert.toStr(object);
	}

	public SysUser createSubject(ReqLoginVo login) {
		AssertUtil.isParamNotNull(ErrorCode.PARAM_NULL, login.getUsername(), login.getPassword());
		// 从SecurityUtils里边创建一个 subject
		Subject subject = SecurityUtils.getSubject();
		// 在认证提交前准备 token（令牌）
		UsernamePasswordToken token = new UsernamePasswordToken(login.getUsername(), SecureUtil.md5(login.getPassword()));
		token.setRememberMe(true);
		// 执行认证登陆
		try {
			subject.login(token);
		} catch (AuthenticationException ae) {
			BusinessException.throwMessage(ae.getMessage());
		}

		if (!subject.isAuthenticated()) {
			token.clear();
			//登录失败
			BusinessException.throwMessage(ErrorCode.LOGIN_ERROR);
		}
		RolesAndPermissionsBo rolesAndPermissionsBo = Convert
				.convert(RolesAndPermissionsBo.class, subject.getPrincipal());
		return Convert.convert(SysUser.class, rolesAndPermissionsBo.getUser());
	}

	public RolesAndPermissionsBo getRolesAndPermissions(Integer userId) {
		//获取角色
		List<SysUserRole> userRoles = roleService.findByUserId(userId);
		List<Integer> roleIds = userRoles.stream().map(SysUserRole::getRoleId).collect(Collectors.toList());
		List<SysRole> roles = roleService.findByStatusAndRoleIdIn(YesOrNoInd.YES.getValue(), roleIds);
		//获取权限
		List<SysPermission> permissions = permissionService.getSysPermission(roleIds);
		return RolesAndPermissionsBo.builder()
				.roles(roles)
				.permissions(permissions)
				.build();

	}

	public void removeSubject() {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
	}

	@Transactional(rollbackFor = BusinessException.class)
	public void deleteByUserId(Integer userId) {
		AssertUtil.isNotNull(userId, ErrorCode.PARAM_NULL);
		//删除所属角色
		roleService.deleteUserRoleByUserId(userId);
		sysUserRepository.deleteByUserId(userId);
	}

	public Pager<RespUseListVo> listPage(ReqUseFilterVo param) {
		return userQueryFactory.listPage(param);
	}

	public SysUser  findByUserId(Integer userId) {
		SysUser user = sysUserRepository.findByUserId(userId);
		AssertUtil.isNotNull(user, ErrorCode.USER_NOT_EXIST);
		return user;
	}

	public void luck(SysUser user, Integer luck) {
		user.setLuck(luck);
		sysUserRepository.save(user);
	}

	public SysUser save(ReqAddUserVo userVo) {
		//用户修改时可不对密码进行修改
		if (StringUtils.isNotBlank(userVo.getUserId())) {
			ValidatorUtil.validateNull(userVo, new String[]{"username"});
		} else {
			ValidatorUtil.validateNull(userVo, new String[]{"username", "password", "nickname", "roleId"});
		}

		SysUser user = sysUserRepository.findByUsername(userVo.getUsername());
		//账号是否重复
		if (ObjectUtil.isNotNull(user)) {
			if (StringUtils.isBlank(userVo.getUserId()) || !userVo.getUserId().equals(user.getUserId())) {
				BusinessException.throwMessage(ErrorCode.NUM_REPEAT);
			}
			CommonBeanUtils.copyProperties(userVo, user);
			if (StringUtils.isNotBlank(userVo.getPassword())) {
				user.setPassword(SecureUtil.md5(userVo.getPassword()));
			}
		} else {
			//修改还是新增
			user = CommonBeanUtils.copyProperties(userVo, SysUser.class);
			user.setLuck(LuckEnum.NO_LUCK.getKey());
			user.setPassword(SecureUtil.md5(userVo.getPassword()));
		}
		return sysUserRepository.save(user);
	}

	public Optional<SysUser> findById(Integer id) {
		return sysUserRepository.findById(id);
	}
}
