package com.microservice.repository.sysuser.pojo.entity;

import com.microservice.repository.common.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ApiModel("系统用户")
@Entity
@Data
@Table(name = "sys_user")
public class SysUser extends BaseEntity implements Serializable, Cloneable {
	/**
	 * id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer userId;
	/**
	 * 用户名
	 */
	private String username;
	/**
	 * 密码
	 */
	private String password;
	/**
	 * 锁定;1-锁定，不允许登录，0-未锁定
	 */
	private Integer luck;
	/**
	 * 昵称
	 */
	private String nickname;

}