package com.microservice.mobile.customer.controller;

import com.microservice.mobile.customer.pojo.vo.req.ReqLoginVo;
import com.microservice.mobile.customer.pojo.vo.resp.RespCustomerLoginVo;
import com.microservice.mobile.customer.process.CustomerUnauthorizedProcess;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author MinWeikai
 * @date 2019/11/4 10:14
 */
@Api(tags = {"客户未授权操作"})
@RequestMapping("common/customer")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CustomerUnauthorizedController {

	@NonNull
	private CustomerUnauthorizedProcess customerUnauthorizedProcess;


	@ApiOperation("登陆")
	@PostMapping("login")
	public RespCustomerLoginVo login(@RequestBody ReqLoginVo loginVo) {
		return customerUnauthorizedProcess.login(loginVo);
	}

}
