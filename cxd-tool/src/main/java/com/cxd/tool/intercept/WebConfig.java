package com.cxd.tool.intercept;

import cn.hutool.core.util.CharsetUtil;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * 请求与返回参数处理
 *
 * @author MinWeikai
 * @date 2019/6/24 10:57
 */
@Configuration
@Slf4j
public class WebConfig implements WebMvcConfigurer {

	/**
	 * 格式化返回体，去除null为空字符串
	 *
	 * @return
	 */
	public HttpMessageConverter fastConverter() {
		//1、定义一个convert转换消息的对象
		FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
		//2、添加fastjson的配置信息
		FastJsonConfig fastJsonConfig = new FastJsonConfig();
		fastJsonConfig.setSerializerFeatures(
				//字符串null返回空字符串
				SerializerFeature.WriteNullStringAsEmpty,
				//数值类型为0
				SerializerFeature.WriteNullNumberAsZero,
				SerializerFeature.WriteDateUseDateFormat,

				//空字段保留
				SerializerFeature.WriteNullListAsEmpty);

		fastJsonConfig.setCharset(CharsetUtil.CHARSET_UTF_8);
		//2-1 处理中文乱码问题
		List<MediaType> fastMediaTypes = new ArrayList<>();
		fastMediaTypes.add(MediaType.APPLICATION_JSON);
		fastConverter.setSupportedMediaTypes(fastMediaTypes);
		//3、在convert中添加配置信息
		fastConverter.setFastJsonConfig(fastJsonConfig);
		return fastConverter;
	}

	@Override
	public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
		converters.clear();
		converters.add(fastConverter());
	}

	/**
	 * 请求拦截处理
	 * addPathPatterns 用于添加拦截规则
	 * excludePathPatterns 用于排除拦截
	 *
	 * @param registry
	 */
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		/**
		 * 跨域拦截器需放在最上面
		 * 解決H5页面OPTIONS问题
		 */
		registry.addInterceptor(new CorsInterceptor()).addPathPatterns("/**");
	}
}