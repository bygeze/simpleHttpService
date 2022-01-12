package com.example.simpleHttpService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
// enable mvc necesario para Swagger
@EnableWebMvc
public class SimpleHttpServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SimpleHttpServiceApplication.class, args);
	}

}

/* 
 * Simple HTTP Service using JPA and H2
 * - since data is persisted in memory I'm using the default config for H2.
 * - model: how an object is gonna be represented by the ORM.
 * 
 */