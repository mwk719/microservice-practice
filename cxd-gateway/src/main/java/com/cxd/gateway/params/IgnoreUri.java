package com.cxd.gateway.params;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 忽略路径
 * 访问权限和返回参数包装
 * 使用nacos进行配置 {@url http://192.168.121.97:8848/nacos/index.html}
 * @author MinWeikai
 * @date 2019/12/6 18:29
 */
@Data
@Component
@RefreshScope
public class IgnoreUri {

	/**
	 * 忽略请求校验的uri
	 */
	@Value("${ignore.check_uri:test}")
	private List<String> ignoreCheckUris;

	/**
	 * 忽略返回参数组装的uri
	 */
	@Value("${ignore.result_uri:data}")
	private List<String> ignoreResultUris;

}
