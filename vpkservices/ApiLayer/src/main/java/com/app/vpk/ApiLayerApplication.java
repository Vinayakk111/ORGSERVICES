package com.app.vpk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

@SpringBootApplication
@OpenAPIDefinition(info = @Info(title = "vpkservices", version = "1.0", description = "API Documentation"))
@EnableScheduling
public class ApiLayerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiLayerApplication.class, args);
		System.out.println("application running fine...");
		
	}

}
