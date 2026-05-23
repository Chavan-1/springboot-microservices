package com.techm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class Day1MicroservicesApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day1MicroservicesApplication.class, args);
	}

}
