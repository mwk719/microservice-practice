package com.cxd.mobile;

import com.cxd.tool.filter.BodyReaderFilter;
import com.cxd.tool.intercept.FeignConfiguration;
import com.cxd.tool.intercept.WebConfig;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManager;

@SpringBootApplication
@EnableDiscoveryClient
@EnableCircuitBreaker
@EnableFeignClients
@EnableJpaAuditing
@EntityScan("com.cxd.repository.*.pojo.entity")
@EnableJpaRepositories(basePackages = "com.cxd.repository.*.dao.repository")
public class CxdMobileApplication {

	public static void main(String[] args) {
		SpringApplication.run(CxdMobileApplication.class, args);
	}

	/**
	 * Spring管理JPAQueryFactory
	 *
	 * @param entityManager
	 * @return
	 */
	@Bean
	public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
		return new JPAQueryFactory(entityManager);
	}

	@Bean
	public FilterRegistrationBean httpServletRequestReplacedRegistration() {
		FilterRegistrationBean registration = new FilterRegistrationBean();
		registration.setFilter(new BodyReaderFilter());
		registration.addUrlPatterns("/*");
		registration.setName("bodyReaderFilter");
		registration.setOrder(1);
		return registration;
	}

	/**
	 * 解决图片上传为空问题
	 *
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
	}

	/**
	 * 配置feign内部服务请求头
	 *
	 * @return
	 */
	@Bean
	public FeignConfiguration feignConfiguration() {
		return new FeignConfiguration();
	}

	/**
	 * 请求与返回参数处理
	 *
	 * @return
	 */
	@Bean
	public WebConfig webConfig() {
		return new WebConfig();
	}

}
