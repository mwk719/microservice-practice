package com.cxd.mobile.customer.process;

import com.cxd.mobile.customer.pojo.vo.resp.RespCustomerReceiptVo;

/**
 * @author MinWeikai
 * @date 2019/11/6 10:38
 */
public interface CustomerProcess {

	 RespCustomerReceiptVo receiptInfo(Integer customerId);


}
