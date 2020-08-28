package com.microservice.repository.customer.dao.repository;

import com.microservice.repository.customer.pojo.entity.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author MinWeikai
 * @date 2019/11/4 10:44
 */
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
	Customer findByUsername(String username);

	void deleteByCustomerId(Integer customerId);

	Customer findByCustomerId(Integer customerId);
}
