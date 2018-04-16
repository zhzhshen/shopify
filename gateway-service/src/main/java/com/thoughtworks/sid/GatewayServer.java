package com.thoughtworks.sid;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.oauth2.client.EnableOAuth2Sso;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.zuul.EnableZuulProxy;

@EnableDiscoveryClient
//@EnableOAuth2Sso
@EnableZuulProxy
@SpringBootApplication
public class GatewayServer {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServer.class, args);
	}
}
