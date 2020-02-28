package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Epaviste;
import com.pfe.star.epave.Repositories.EpavisteRepository;
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
@RequestMapping("/epaviste")
public class EpavisteController {
    private final Logger log = LoggerFactory.getLogger(EpavisteController.class);
    @Autowired
    private final EpavisteRepository Epav_repo;
    public EpavisteController(EpavisteRepository epav_repo){
        Epav_repo=epav_repo;
    }
    @GetMapping("/liste_epav")
    public Collection<Epaviste> liste_epav(){
        return Epav_repo.findAll();
    }
    @GetMapping("/epaviste/{id}")
    public ResponseEntity<?> ex_ById(@PathVariable("id") Long id) {
        Optional<Epaviste> epav = Epav_repo.findById(id);
        return epav.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/ajouter_epav")
    public ResponseEntity<Epaviste> ajouter_epav(@Valid @RequestBody Epaviste epav) throws URISyntaxException {
        log.info("Ajouter un nouveau Epaviste", epav);
        Epaviste result = Epav_repo.save(epav);
        return ResponseEntity.created(new URI("/ajouter_epav" + result.getId())).body(result);
    }
    @PutMapping("modifier_epav")
    public ResponseEntity<Epaviste> modifier_epav(@Valid @RequestBody Epaviste epav, @PathVariable("id") long id) {
        log.info("Modifier Epaviste", epav);
        Optional<Epaviste> epavOptional = Epav_repo.findById(id);
        if (epavOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Epaviste e = epavOptional.get();
        e.setNom(epav.getNom());
        e.setPrenom(epav.getPrenom());
        e.setLogin(epav.getLogin());
        e.setPassword(epav.getPassword());
        Epaviste result= Epav_repo.save(e);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("supprimer_epav")
    public ResponseEntity<?>supprimer_epav(@PathVariable("id")String id){
        log.info("Supprimer Epaviste",id);
        List<Epaviste> tt_epav= Epav_repo.findAll().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        for (Epaviste e: tt_epav)
            Epav_repo.deleteById((e.getId()));
        return ResponseEntity.ok().build();
    }
}
