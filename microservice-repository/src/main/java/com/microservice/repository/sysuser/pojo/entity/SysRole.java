package com.microservice.repository.sysuser.pojo.entity;

import com.microservice.repository.common.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ApiModel("系统角色")
@Entity
@Data
@Table(name = "sys_role")
public class SysRole extends BaseEntity implements Serializable, Cloneable {
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer roleId;
	/**
	 * 角色名
	 */
	private String role;
	/**
	 * 状态;1-可用，0-不可用
	 */
	private Integer status;

	/**
	 * 描述
	 */
	private String description;

}