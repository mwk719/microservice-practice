package com.microservice.web.seata.service;

import com.microservice.repository.customer.pojo.entity.CustomerReceipt;
import com.microservice.web.customer.service.CustomerReceiptService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
	private final CustomerReceiptService customerReceiptService;

	@Transactional(rollbackFor = Exception.class)
	public void saveTest() {
		CustomerReceipt receipt = new CustomerReceipt();
		receipt.setCustomerId(666);
		receipt.setAddress("事务测试");
		receipt.setConsignee("事务测试");
		receipt.setMobilePhone("事务测试");
		customerReceiptService.save(receipt);
		throw new RuntimeException("分布式事务正常回滚");
	}

}
