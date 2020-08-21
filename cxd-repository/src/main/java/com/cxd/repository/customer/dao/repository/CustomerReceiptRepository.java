package com.cxd.repository.customer.dao.repository;

import com.cxd.repository.customer.pojo.entity.CustomerReceipt;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/4 11:22
 */
@Repository
public interface CustomerReceiptRepository extends JpaRepository<CustomerReceipt, Integer> {
	List<CustomerReceipt> findByCustomerId(Integer customerId);
}
