package com.techm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
public class Day3MicroservicesPaymentServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(Day3MicroservicesPaymentServiceApplication.class, args);
	}

}
