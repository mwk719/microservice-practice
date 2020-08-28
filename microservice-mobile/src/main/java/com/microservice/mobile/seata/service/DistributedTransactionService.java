package com.microservice.mobile.seata.service;

import com.microservice.mobile.seata.feign.DistributedTransactionFeign;
import com.microservice.repository.customer.dao.repository.CustomerRepository;
import com.microservice.repository.customer.pojo.entity.Customer;
import io.seata.spring.annotation.GlobalTransactional;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 *
 * @author MinWeikai
 * @date  2020-08-18 16:39:05
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedTransactionService {

	@NonNull
	private final CustomerRepository customerRepository;

	@NonNull
	private final DistributedTransactionFeign distributedTransactionFeign;


	/**
	 * @GlobalTransactional 使用该注解才算使用了分布式事务
	 * 使用注解开启分布式事务时，若要求事务回滚，必须将异常抛出到事务的发起方，
	 * 被事务发起方的 @GlobalTransactional 注解感知到
	 */
	@GlobalTransactional
	public void saveTest() {
		Customer customer = new Customer();
		customer.setNickname("事务测试");
		customer.setUsername("事务测试");
		customer.setPassword("事务测试");
		customer.setLuck(1);
		customer.setMobilePhone("事务测试");
		customerRepository.save(customer);

		distributedTransactionFeign.saveTest();
	}

}
