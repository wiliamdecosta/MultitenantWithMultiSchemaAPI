package com.justclick;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan("com.justclick.*")
public class MultitenantServicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(MultitenantServicesApplication.class, args);
	}

}
