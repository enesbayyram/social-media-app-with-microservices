package com.enesbayram.sm_api_gateway.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.enesbayram.*"})
@EntityScan(basePackages = {"com.enesbayram.*"})
@EnableJpaRepositories(basePackages = {"com.enesbayram.*"})
public class SmApiGatewayStarter {

	public static void main(String[] args) {
		SpringApplication.run(SmApiGatewayStarter.class, args);
	}

}
