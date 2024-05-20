package com.microservice.moto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MicroserviceMotoApplication {

	public static void main(String[] args) {
		SpringApplication.run(MicroserviceMotoApplication.class, args);
	}

}
