package com.pfe.star.epave;


import com.pfe.star.epave.Controllers.OffreController;
import com.pfe.star.epave.Models.Offre;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;


@SpringBootApplication
public class EpaveApplication {


	public static void main(String[] args) {
		SpringApplication.run(EpaveApplication.class, args);
	}

	@Autowired
	private OffreController offreservice;

	/*@Override
	public void run(String... args) throws Exception {
		 Offre o = offreservice.offre_best((long) 55);
		 o.setOffreAcceptee(true);
		 offreservice.modifier_montant(o,o.getId().getEpavisteId(),o.getId().getEpavisteId());
		 System.out.println(o);

	}*/
}