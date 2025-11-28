package com.enesbayram.sm_comment_manager.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@ComponentScan(basePackages = {"com.enesbayram.*"})
@EntityScan(basePackages = {"com.enesbayram.*"})
@EnableJpaRepositories(basePackages = {"com.enesbayram.*"})
@EnableFeignClients(basePackages = {"com.enesbayram.sm_client.*"})
@SpringBootApplication
public class SmCommentManagerStarter {

	public static void main(String[] args) {
		SpringApplication.run(SmCommentManagerStarter.class, args);
	}

}
