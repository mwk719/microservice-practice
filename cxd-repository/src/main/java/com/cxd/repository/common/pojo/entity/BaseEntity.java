package com.cxd.repository.common.pojo.entity;

import lombok.Data;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import java.io.Serializable;
import java.util.Date;

/**
 * @author MinWeikai
 * @date 2019/7/23 14:42
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity implements Serializable {

	/**
	 * 创建时间
	 */
	@CreatedDate
	@Column(nullable = false,columnDefinition="datetime(0) COMMENT '创建时间'")
	private Date createTime;


	/**
	 * 更新时间
	 */
	@LastModifiedDate
	@Column(nullable = false,columnDefinition="datetime(0) COMMENT '更新时间'")
	private Date updateTime;

}
