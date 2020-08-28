package com.microservice.mobile.customer.process.impl;

import com.microservice.mobile.customer.pojo.vo.req.ReqLoginVo;
import com.microservice.mobile.customer.pojo.vo.resp.RespCustomerLoginVo;
import com.microservice.mobile.customer.process.CustomerUnauthorizedProcess;
import com.microservice.mobile.customer.service.CustomerService;
import com.microservice.repository.customer.pojo.entity.Customer;
import com.microservice.tool.util.CommonBeanUtils;
import com.microservice.tool.util.JwtUtil;
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

		RespCustomerLoginVo customerLoginVo = CommonBeanUtils.copyProperties(customer, RespCustomerLoginVo.class);
		//生成key并返回
		String key = JwtUtil.buildJWT(String.valueOf(customerLoginVo.getCustomerId()), customerLoginVo.getUsername());
		customerLoginVo.setKey(key);
		return customerLoginVo;
	}
}
