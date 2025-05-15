package com.soft.recipservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class RecipServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(RecipServiceApplication.class, args);
	}

}
