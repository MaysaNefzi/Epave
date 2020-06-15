package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.RapportFinal;
import com.pfe.star.epave.Models.Sinistre;
import com.pfe.star.epave.Repositories.RapportFinalRepository;
import com.pfe.star.epave.Repositories.RapportRepository;
import com.pfe.star.epave.Repositories.SinistreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/rech")

public class RechController {
    private final Logger log = LoggerFactory.getLogger(PoliceController.class);
    @Autowired
    private final SinistreRepository Sin_repo;
    private final RapportRepository Rap_repo;
    private final RapportFinalRepository RapF_repo;
    public RechController(SinistreRepository s_repo , RapportRepository r_repo , RapportFinalRepository rf_repo){
        Sin_repo=s_repo;
        Rap_repo =r_repo;
        RapF_repo =rf_repo;
    }
    @GetMapping(value = "/sin_byMarque/{recherche}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Sinistre> sinistreBymarque(@PathVariable String recherche) {
        return Sin_repo.findByMarqueLike("%"+recherche+"%");
    }
    @GetMapping(value = "/sin_byModele/{recherche}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Sinistre> sinistreBymodele(@PathVariable String recherche) {
        return Sin_repo.findByModeleLike("%"+recherche+"%");
    }
    @GetMapping(value = "/sin_byImm/{recherche}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Sinistre sinistreByImm(@PathVariable String recherche) {
        Sinistre s = new Sinistre();
        s=Sin_repo.Sin_ByImm(recherche);
        return s;
    }
    @GetMapping(value = "/RappF_By_Epv/{nom}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<RapportFinal> RappfByEpv(@PathVariable String nom) {
        return  RapF_repo.Rapport_By_Epv(nom) ;
    }
}
