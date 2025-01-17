package com.pranavv51.microservice.xlreadmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

@SpringBootApplication
@EnableFeignClients
@CrossOrigin
public class XlreadMicroserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(XlreadMicroserviceApplication.class, args);
	}

}
