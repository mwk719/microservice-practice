package com.microservice.web;

import com.microservice.tool.filter.BodyReaderFilter;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import javax.persistence.EntityManager;

@EnableJpaAuditing
@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
@EntityScan("com.microservice.repository.*.pojo.entity")
@EnableJpaRepositories(basePackages = "com.microservice.repository.*.dao.repository")
public class MicroserviceWebApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceWebApplication.class, args);
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

	/**
	 * 解决图片上传为空问题
	 *
	 * @return
	 */
	@Bean(name = "multipartResolver")
	public CommonsMultipartResolver multipartResolver() {
		return new CommonsMultipartResolver();
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
}
