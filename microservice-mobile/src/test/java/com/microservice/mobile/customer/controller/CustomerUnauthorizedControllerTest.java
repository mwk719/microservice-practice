package com.microservice.mobile.customer.controller;

import cn.hutool.core.util.ObjectUtil;
import com.microservice.mobile.customer.pojo.vo.req.ReqLoginVo;
import com.microservice.mobile.customer.pojo.vo.resp.RespCustomerLoginVo;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author MinWeikai
 * @date 2020/8/24 16:51
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerUnauthorizedControllerTest {

	@Autowired
	private CustomerUnauthorizedController customerUnauthorizedController;

	@Test
	public void loginTest() {
		ReqLoginVo loginVo = new ReqLoginVo();
		loginVo.setUsername("13333333333");
		loginVo.setPassword("123456");
		RespCustomerLoginVo respCustomerLoginVo = customerUnauthorizedController.login(loginVo);
		Assert.assertTrue(ObjectUtil.isNotNull(respCustomerLoginVo.getCustomerId()));
		log.info("登录用户信息：{}", respCustomerLoginVo);
	}


}