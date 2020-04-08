package com.pfe.star.epave;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EpaveApplication {

	public static void main(String[] args) {
		SpringApplication.run(EpaveApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCPE(){
		return  new BCryptPasswordEncoder();
	}
}
