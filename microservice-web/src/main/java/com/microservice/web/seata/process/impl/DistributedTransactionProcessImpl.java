package com.microservice.web.seata.process.impl;

import com.microservice.web.seata.process.DistributedTransactionProcess;
import com.microservice.web.seata.service.DistributedTransactionService;
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
		distributedTransactionService.saveTest();
	}
}
