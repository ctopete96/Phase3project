package com.example.templateconfigserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.config.server.EnableConfigServer;

@SpringBootApplication
@EnableConfigServer
public class TemplateconfigserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(TemplateconfigserverApplication.class, args);
	}

}
