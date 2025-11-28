package com.enesbayram.sm_config_server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class SmConfigServerStarter {
	public static void main(String[] args) {
		SpringApplication.run(SmConfigServerStarter.class, args);
	}
}
