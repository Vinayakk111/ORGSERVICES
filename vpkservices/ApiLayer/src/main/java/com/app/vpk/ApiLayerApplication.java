package com.app.vpk;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ApiLayerApplication {

	public static void main(String[] args) {

		SpringApplication.run(ApiLayerApplication.class, args);
		System.out.println("application running fine...");
		
	}

}
