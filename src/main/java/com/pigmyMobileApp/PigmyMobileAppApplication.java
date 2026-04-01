package com.pigmyMobileApp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan
public class PigmyMobileAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(PigmyMobileAppApplication.class, args);
	}

}
