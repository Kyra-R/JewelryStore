package com.example.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecurityApplication {
	static final Logger log =
			LoggerFactory.getLogger(SecurityApplication.class);

	public static void main(String[] args) {

		log.info("Before Starting application");
		SpringApplication.run(SecurityApplication.class, args);
		log.warn("Starting my application!");
	}

}
