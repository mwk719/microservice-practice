package com.cxd.mobile.customer.process.impl;

import com.cxd.mobile.customer.pojo.vo.req.ReqLoginVo;
import com.cxd.mobile.customer.pojo.vo.resp.RespCustomerLoginVo;
import com.cxd.mobile.customer.process.CustomerUnauthorizedProcess;
import com.cxd.mobile.customer.service.CustomerService;
import com.cxd.repository.customer.pojo.entity.Customer;
import com.cxd.tool.util.CommonBeanUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author MinWeikai
 * @date 2019/11/6 10:24
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class CustomerUnauthorizedProcessImpl implements CustomerUnauthorizedProcess {

	@NonNull
	private CustomerService customerService;

	@Override
	public RespCustomerLoginVo login(ReqLoginVo loginVo) {
		//验证用户是否存在
		Customer customer = customerService.findByUsername(loginVo);

		//校验密码
		customerService.checkPassword(loginVo.getPassword(), customer.getPassword());

		//生成key并返回
		//String key = customerService.createKey(customer);
		//customerLoginVo.setKey(key);
		return CommonBeanUtils.copyProperties(customer, RespCustomerLoginVo.class);

	}
}
