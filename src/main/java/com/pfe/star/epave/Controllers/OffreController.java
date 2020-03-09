package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.OffreID;
import com.pfe.star.epave.Repositories.OffreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/offre")
public class OffreController {
    private final Logger log = LoggerFactory.getLogger(OffreController.class);
    @Autowired
    private final OffreRepository Offre_repo;
    public OffreController(OffreRepository offre_repo){
        Offre_repo=offre_repo;
    }
    @GetMapping("/liste_offre")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Offre> liste_offre(){
        return Offre_repo.findAll();
    }

    @PostMapping("/ajouter_offre")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Offre> ajouter_offre(@Valid @RequestBody Offre offre) throws URISyntaxException {
        log.info("Ajouter une nouvelle Offre", offre);
        Offre result = Offre_repo.save(offre);
        return ResponseEntity.created(new URI("/ajouter_offre" + result.getId())).body(result);
    }
    @PutMapping("modifier_montant")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Offre> modifier_montant(@Valid @RequestBody Offre offre, @PathVariable("id") long id) {
        log.info("Modifier Offre", offre);
        Optional<Offre> offreOptional = Offre_repo.findById(id);
        if (offreOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Offre of = offreOptional.get();
        of.setMontant(offre.getMontant());
        Offre result= Offre_repo.save(of);
        return ResponseEntity.ok().body(result);
    }


}
