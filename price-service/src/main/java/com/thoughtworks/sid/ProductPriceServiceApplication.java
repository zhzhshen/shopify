package com.thoughtworks.sid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
//@EnableOAuth2Sso
@SpringBootApplication
public class ProductPriceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductPriceServiceApplication.class, args);
	}
}
