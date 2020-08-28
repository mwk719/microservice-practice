package com.microservice.mobile.customer.process;

import com.microservice.mobile.customer.pojo.vo.resp.RespCustomerReceiptVo;

/**
 * @author MinWeikai
 * @date 2019/11/6 10:38
 */
public interface CustomerProcess {

	 RespCustomerReceiptVo receiptInfo(Integer customerId);


}
