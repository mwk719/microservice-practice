package com.cxd.mobile.customer.controller;

import com.cxd.mobile.customer.pojo.vo.resp.RespCustomerReceiptVo;
import com.cxd.mobile.customer.process.CustomerProcess;
import com.cxd.tool.vo.req.ReqParamsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * @author MinWeikai
 * @date 2019/11/4 10:14
 */
@Api(tags = {"客户操作"})
@RequestMapping("approve/customer")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class CustomerController {

	@NonNull
	private CustomerProcess customerProcess;

	@ApiOperation("收货信息")
	@PostMapping("receiptInfo")
	public RespCustomerReceiptVo receiptInfo(@RequestBody ReqParamsVo paramsVo) {
		return customerProcess.receiptInfo(paramsVo.getCommon().getCustomerId());
	}

}
