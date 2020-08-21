package com.cxd.mobile.customer.service;

import com.cxd.mobile.customer.pojo.vo.resp.RespCustomerReceiptVo;
import com.cxd.repository.customer.dao.repository.CustomerReceiptRepository;
import com.cxd.repository.customer.pojo.entity.CustomerReceipt;
import com.cxd.tool.util.CollectionUtil;
import com.cxd.tool.util.CommonBeanUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author MinWeikai
 * @date 2019/11/5 15:30
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerReceiptService {

	@NonNull
	private CustomerReceiptRepository customerReceiptRepository;

	public RespCustomerReceiptVo receiptInfo(Integer customerId) {
		CustomerReceipt receipt = this.findByCustomerId(customerId);
		return CommonBeanUtils.copyProperties(receipt, RespCustomerReceiptVo.class);
	}

	public CustomerReceipt findByCustomerId(Integer customerId) {
		List<CustomerReceipt> receipts = customerReceiptRepository.findByCustomerId(customerId);
		if (CollectionUtil.isEmpty(receipts)) {
			return null;
		}
		//业务需要暂时用户与地址一对一
		return receipts.get(0);
	}
}
