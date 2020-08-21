package com.cxd.gateway.params;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 忽略路径
 * 访问权限和返回参数包装
 * @author MinWeikai
 * @date 2019/12/6 18:29
 */
@Data
@Component
@RefreshScope
public class IgnoreUri {

	@Value("${ignore.check_uri:test}")
	private List<String> ignoreCheckUris;

}
