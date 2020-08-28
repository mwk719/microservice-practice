package com.microservice.scheduled;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan("com.microservice.repository.*.pojo.entity")
@EnableJpaRepositories(basePackages = "com.microservice.repository.*.dao.repository")
public class MicroserviceScheduledApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceScheduledApplication.class, args);
	}

}
