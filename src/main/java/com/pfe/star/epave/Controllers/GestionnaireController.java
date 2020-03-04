package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Gestionnaire;
import com.pfe.star.epave.Repositories.GestionnaireRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
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

    @GetMapping("/gestionnaire/{id}")
    public ResponseEntity<?> gest_ById(@PathVariable("id") Long id) {
        Optional<Gestionnaire> gest = Gest_repo.findById(id);
        return gest.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/ajouter_gest")
    public ResponseEntity<Gestionnaire> ajouter_gest(@Valid @RequestBody Gestionnaire gest) throws URISyntaxException {
        log.info("Ajouter un nouveau gestionnaire", gest);
        Gestionnaire result = Gest_repo.save(gest);
        return ResponseEntity.created(new URI("/ajouter_gest" + result.getId())).body(result);
    }
    @PutMapping("modifier_gest")
    public ResponseEntity<Gestionnaire> modifier_gest(@Valid @RequestBody Gestionnaire gest, @PathVariable("id") long id) {
        log.info("Modifier Gestionnaire", gest);
        Optional<Gestionnaire> gestOptional = Gest_repo.findById(id);
        if (gestOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Gestionnaire g = gestOptional.get();
        g.setNom(gest.getNom());
        g.setPrenom(gest.getPrenom());
        g.setLogin(gest.getLogin());
        g.setPassword(gest.getPassword());
        Gestionnaire result= Gest_repo.save(g);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("supprimer_gest")
    public ResponseEntity<?>supprimer_gest(@PathVariable("id")String id){
        log.info("Supprimer Gestionnaire",id);
        List<Gestionnaire> tt_gest= Gest_repo.findAll().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        for (Gestionnaire g: tt_gest)
            Gest_repo.deleteById((g.getId()));
        return ResponseEntity.ok().build();
    }



}
