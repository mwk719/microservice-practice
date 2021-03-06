package com.microservice.mobile.seata.controller;

import com.microservice.mobile.seata.process.DistributedTransactionProcess;
import com.microservice.tool.vo.req.ReqParamsVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 该目录属于测试，可用于参照
 * @author MinWeikai
 * @date  2020-08-18 16:27:47
 */
@Api(tags = {"分布式事务测试"})
@RequestMapping("test/distributedTransaction")
@RestController
@CrossOrigin
@RequiredArgsConstructor
public class DistributedTransactionController {

	@NonNull
	private final DistributedTransactionProcess distributedTransactionProcess;

	/**
	 * microservice-web出问题 -> microservice-mobile回滚
	 */
	@ApiOperation("客户信息保存测试")
	@PostMapping("saveTest")
	public void saveTest(@RequestBody ReqParamsVo paramsVo) {
		distributedTransactionProcess.saveTest();
	}

}
