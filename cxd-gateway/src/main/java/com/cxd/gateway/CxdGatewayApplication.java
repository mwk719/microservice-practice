package com.cxd.gateway;

import com.cxd.tool.util.RedisSpringProxy;
import lombok.NonNull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class CxdGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(CxdGatewayApplication.class, args);
	}

	@Bean
	public RedisSpringProxy redisSpringProxy(@NonNull RedisTemplate redisTemplate
			, @NonNull StringRedisTemplate stringRedisTemplate) {
		return new RedisSpringProxy(redisTemplate, stringRedisTemplate);
	}

}
