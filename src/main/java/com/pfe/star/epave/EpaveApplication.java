package com.pfe.star.epave;

import com.pfe.star.epave.Models.Utilisateur;
import com.pfe.star.epave.Services.CompteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootApplication
public class EpaveApplication implements CommandLineRunner {
	@Autowired
	private CompteService compteService;

	public static void main(String[] args) {
		SpringApplication.run(EpaveApplication.class, args);
	}

	@Bean
	public BCryptPasswordEncoder getBCPE(){
		return  new BCryptPasswordEncoder();
	}

	@Override
	public void run(String... args) throws Exception {
		compteService.saveUser(new Utilisateur("1234567","admin","1234","maysa","may","ADMIN","maysa@gmail.com"));

	}
}
