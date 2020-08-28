package com.microservice.web.customer.service;

import com.microservice.repository.customer.dao.repository.CustomerReceiptRepository;
import com.microservice.repository.customer.pojo.entity.CustomerReceipt;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MinWeikai
 * @date 2019/11/11 17:49
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerReceiptService {

	@NonNull
	private CustomerReceiptRepository customerReceiptRepository;

	public void save(CustomerReceipt receipt) {
		customerReceiptRepository.save(receipt);
	}
}
