package com.microservice.repository.sysuser.pojo.entity;

import com.microservice.repository.common.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ApiModel("用户角色")
@Entity
@Data
@Table(name = "sys_user_role")
public class SysUserRole extends BaseEntity implements Serializable, Cloneable {
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 用户id
	 */
	private Integer userId;
	/**
	 * 角色id
	 */
	private Integer roleId;

}