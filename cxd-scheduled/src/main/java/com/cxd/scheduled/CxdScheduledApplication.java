package com.cxd.scheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.cxd.repository.*.pojo.entity")
@EnableJpaRepositories(basePackages = "com.cxd.repository.*.dao.repository")
public class CxdScheduledApplication {

	public static void main(String[] args) {
		SpringApplication.run(CxdScheduledApplication.class, args);
	}

}
