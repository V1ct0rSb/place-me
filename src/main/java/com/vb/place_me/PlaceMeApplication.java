package com.vb.place_me;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class PlaceMeApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlaceMeApplication.class, args);
	}

}
