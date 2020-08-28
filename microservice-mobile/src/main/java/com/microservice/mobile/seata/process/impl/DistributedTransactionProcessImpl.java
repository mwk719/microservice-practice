package com.microservice.mobile.seata.process.impl;

import com.microservice.mobile.seata.process.DistributedTransactionProcess;
import com.microservice.mobile.seata.service.DistributedTransactionService;
import com.microservice.tool.exception.BusinessException;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MinWeikai
 * @date 2020-08-18 16:31:03
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class DistributedTransactionProcessImpl implements DistributedTransactionProcess {

	@NonNull
	private final DistributedTransactionService distributedTransactionService;

	@Override
	public void saveTest() {
		try {
			distributedTransactionService.saveTest();
		}catch (Exception e){
			BusinessException.throwMessage("分布式事务 客户信息保存测试 执行成功");
		}
	}
}
