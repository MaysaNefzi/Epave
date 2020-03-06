package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Gestionnaire;
import com.pfe.star.epave.Repositories.GestionnaireRepository;
import javassist.NotFoundException;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/gestionnaire")
public class GestionnaireController {
    private final Logger log = LoggerFactory.getLogger(GestionnaireController.class);
    @Autowired
    private final GestionnaireRepository Gest_repo;
    public GestionnaireController(GestionnaireRepository gest_repo){
        Gest_repo=gest_repo;
    }
    @GetMapping("/liste_gest")
    public Collection<Gestionnaire> liste_gest(){
        return Gest_repo.findAll();
    }

    @GetMapping("/gestionnaire/{cin}")
    public ResponseEntity<?> gest_ById(@PathVariable("cin") Long cin) {
        Optional<Gestionnaire> gest = Gest_repo.findById(cin);
        return gest.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/gestByMatr/{mat}")
    public Collection<Gestionnaire> gestByMatr(@PathVariable String mat) {
        return Gest_repo.findAll().stream().filter(x -> x.getMatricule().equals(mat)).collect(Collectors.toList());

    }
    @PostMapping("/ajouter_gest")
    public ResponseEntity<Gestionnaire> ajouter_gest(@Valid @RequestBody Gestionnaire gest) throws URISyntaxException {
        log.info("Ajouter un nouveau gestionnaire", gest);
        Gestionnaire result = Gest_repo.save(gest);
        return ResponseEntity.created(new URI("/ajouter_gest" + result.getCin())).body(result);
    }
    @PutMapping("/modifier_gest/{cin}")
    public ResponseEntity<Gestionnaire> modifier_gest(@Valid @RequestBody Gestionnaire gest, @PathVariable("cin") long cin) {
        log.info("Modifier Gestionnaire", gest);
        Optional<Gestionnaire> gestOptional = Gest_repo.findById(cin);
        if (gestOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Gestionnaire g = gestOptional.get();
        g.setCin(gest.getCin());
        g.setId(gest.getId());
        g.setMatricule(gest.getMatricule());
        g.setNom(gest.getNom());
        g.setPrenom(gest.getPrenom());
        g.setAdmin(gest.isAdmin());
        g.setUsername(gest.getUsername());
        g.setPassword(gest.getPassword());
        Gestionnaire result= Gest_repo.save(g);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_gest/{cin}")
    public Map<String, Boolean> supprimer_gest(@PathVariable Long cin) {
        Gestionnaire g = null;
        try {
            g = Gest_repo.findById(cin)
                    .orElseThrow(() -> new NotFoundException("Rapport introuvable pour cette cin: " + cin));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        Gest_repo.delete(g);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Gestionnaire supprimé avec succes", Boolean.TRUE);
        return response;
    }



}
