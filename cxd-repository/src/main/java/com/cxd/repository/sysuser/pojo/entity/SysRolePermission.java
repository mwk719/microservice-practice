package com.cxd.repository.sysuser.pojo.entity;

import com.cxd.repository.common.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@ApiModel("角色权限")
@Entity
@Data
@Table(name = "sys_role_permission")
public class SysRolePermission extends BaseEntity implements Serializable, Cloneable {
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	/**
	 * 角色id
	 */
	private Integer roleId;
	/**
	 * 权限id
	 */
	private Integer permissionId;

}