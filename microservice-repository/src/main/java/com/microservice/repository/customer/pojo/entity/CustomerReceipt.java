package com.microservice.repository.customer.pojo.entity;

import com.microservice.repository.common.pojo.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.io.Serializable;

@EqualsAndHashCode(callSuper = true)
@ApiModel("客户收货信息")
@Entity
@Data
@Table(name = "customer_receipt")
public class CustomerReceipt extends BaseEntity implements Serializable, Cloneable {
	/**
	 * 主键id
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer receiptId;
	/**
	 * 客户ID
	 */
	private Integer customerId;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 手机号
	 */
	private String mobilePhone;
	/**
	 * 收货地址
	 */
	private String address;

}