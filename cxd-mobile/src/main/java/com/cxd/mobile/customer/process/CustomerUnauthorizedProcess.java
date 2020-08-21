package com.cxd.mobile.customer.process;

import com.cxd.mobile.customer.pojo.vo.req.ReqLoginVo;
import com.cxd.mobile.customer.pojo.vo.resp.RespCustomerLoginVo;

/**
 * @author MinWeikai
 * @date 2019/11/6 10:23
 */
public interface CustomerUnauthorizedProcess {

	RespCustomerLoginVo login(ReqLoginVo loginVo);
}
