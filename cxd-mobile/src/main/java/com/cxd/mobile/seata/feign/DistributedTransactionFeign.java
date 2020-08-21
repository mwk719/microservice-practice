package com.cxd.mobile.seata.feign;

import com.cxd.mobile.common.config.MultipartSupportConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author MinWeikai
 * @date 2019/12/9 14:21
 */
@FeignClient(name = "cxd-web", configuration = {MultipartSupportConfig.class}/*, fallback = HystrixClientFallback.class*/)
public interface DistributedTransactionFeign {

	@PostMapping("/test/distributedTransaction/saveTest")
	void saveTest();

}
