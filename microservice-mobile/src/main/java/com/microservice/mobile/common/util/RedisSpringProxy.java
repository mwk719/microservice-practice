package com.microservice.mobile.common.util;

import lombok.NonNull;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author MinWeikai
 * @date 2019/9/11 15:35
 */
@Component
public class RedisSpringProxy extends com.microservice.tool.util.RedisSpringProxy {

	public RedisSpringProxy(@NonNull RedisTemplate redisTemplate, @NonNull StringRedisTemplate stringRedisTemplate) {
		super(redisTemplate, stringRedisTemplate);
	}
}
