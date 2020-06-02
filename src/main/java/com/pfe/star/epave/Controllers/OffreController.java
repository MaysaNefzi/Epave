package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.OffreID;
import com.pfe.star.epave.Models.Vente;
import com.pfe.star.epave.Repositories.OffreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
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

    @GetMapping("/offre_order/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Offre> offre_order(@PathVariable("id") Long id){
        return Offre_repo.getOffresoreder(id);
    }

    @GetMapping("/offre_best/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Offre offre_best(@PathVariable("id") Long id){
        return Offre_repo.getOffresoreder(id).get(0);
    }
    @GetMapping("/offre_ById/{num}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Offre offre_ById(@PathVariable("num") Long numOffre  ) {
        return Offre_repo.OffreById(numOffre);
    }
    @GetMapping("/isbest/{idV}/{num}")
    @CrossOrigin(origins = "http://localhost:4200")
    public boolean isBest(@PathVariable("idV")Long idV,@PathVariable("num") Long numOffre){
        Offre offreM =offre_best(idV);
        Offre offre= offre_ById(numOffre);
        return offreM.equals(offre);
    }

    @GetMapping("/offre_ByEpav/{idE}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Offre> offre_ByEpv(Long idE ) {
        return Offre_repo.OffreByEpv(idE);
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
        //of.setId(id);
        of.setDateOffre(offre.getDateOffre());
        of.setEpaviste(offre.getEpaviste());
        of.setVente(offre.getVente());
        of.setOffreAcceptee(offre.isOffreAcceptee());
        of.setUrlJustificatif(offre.getUrlJustificatif());
        of.setMontant(offre.getMontant());
        of.setCommentaire(offre.getCommentaire());
        Offre result= Offre_repo.save(of);
        return ResponseEntity.ok().body(result);
    }




}
