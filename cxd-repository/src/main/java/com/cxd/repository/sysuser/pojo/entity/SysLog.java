package com.cxd.repository.sysuser.pojo.entity;

import com.cxd.repository.common.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;


@EqualsAndHashCode(callSuper = true)
@ApiModel("undefined")
@Entity
@Data
@Table(name = "sys_log")
public class SysLog extends BaseEntity implements Serializable, Cloneable {
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
	 * 用户名
	 */
	private String username;
	/**
	 * 昵称
	 */
	private String nickname;
	/**
	 * 角色名
	 */
	private String roleName;
	/**
	 *
	 */
	private String ip;
	/**
	 * 操作名称
	 */
	private String operateName;
	/**
	 * 操作路径
	 */
	private String uri;
	/**
	 * 参数
	 */
	private String params;

}