package com.microservice.mobile.common.advice;

import com.microservice.tool.advice.MyResponseBodyAdvice;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 修饰返回体
 *
 * @author MinWeikai
 * @date 2019/9/11 14:58
 */
@RestControllerAdvice(basePackages = "com.microservice.mobile")
public class ResponseBodyAdvice extends MyResponseBodyAdvice {
}
