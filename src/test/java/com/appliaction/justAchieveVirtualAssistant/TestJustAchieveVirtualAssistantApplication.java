package com.appliaction.justAchieveVirtualAssistant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;

@TestConfiguration(proxyBeanMethods = false)
class TestJustAchieveVirtualAssistantApplication {

	@Bean
	@ServiceConnection
	PostgreSQLContainer<?> postgresContainer() {
		return new PostgreSQLContainer<>("postgres:latest");
	}

	public static void main(String[] args) {
		SpringApplication.from(JustAchieveVirtualAssistantApplication::main).with(TestJustAchieveVirtualAssistantApplication.class).run(args);
	}

}
