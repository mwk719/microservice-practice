package com.microservice.repository.sysuser.pojo.entity;

import com.microservice.repository.common.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@ApiModel("系统权限")
@Entity
@Data
@Table(name = "sys_permission")
public class SysPermission extends BaseEntity implements Serializable, Cloneable {
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer permissionId;
	/**
	 * 权限名
	 */
	private String name;
	/**
	 * 资源路径
	 */
	private String url;
	/**
	 * 权限字符串;role:create
	 */
	private String permission;
	/**
	 * 父编号
	 */
	private Integer parentId;
	/**
	 * 资源类型;[menu|button]
	 */
	private String resourceType;
	/**
	 *
	 */
	private Integer sort;

}