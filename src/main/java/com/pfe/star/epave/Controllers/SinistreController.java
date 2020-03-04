package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Sinistre;
import com.pfe.star.epave.Repositories.SinistreRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/sinistre")
public class SinistreController {
    private final Logger log = LoggerFactory.getLogger(SinistreController.class);
    @Autowired
    private final SinistreRepository Sin_repo;
    private boolean epave= true;
    public SinistreController(SinistreRepository sin_repo){
        Sin_repo=sin_repo;
    }

    @GetMapping("/liste_sin")
    public Collection<Sinistre> liste_sin(){
        return Sin_repo.findAll();
    }

    @GetMapping("/sinistre/{police}")
    public ResponseEntity<?> sin_ByPolice(@PathVariable("police") Long police) {
        Optional<Sinistre> sin = Sin_repo.findById(police);
        return sin.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sinistre/{numeroSinistre}")
    public ResponseEntity<?> sin_ByNumSinistre(@PathVariable("numeroSinistre") Long numeroSinistre) {
        Optional<Sinistre> sin = Sin_repo.findById(numeroSinistre);
        return sin.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/sinistre/{immatriculation}")
    public Collection<Sinistre> Sin_ByImmatriculation(@PathVariable String immatriculation) {
        return Sin_repo.findAll().stream().filter(x -> x.getImmatriculation().equals(immatriculation)).collect(Collectors.toList());

    }
    private boolean est_Epave(Sinistre sin) {
        return sin.getEpave().equals(epave);
    }
    private boolean non_Epave(Sinistre sin){
        return sin.getEpave().equals(!epave);
    }

    @GetMapping("/tt_Epave")
    public Collection<Sinistre> tt_Epave() {
        return Sin_repo.findAll().stream().filter(this::est_Epave).collect(Collectors.toList());
    }
    @GetMapping("/tt_nonEpave")
    public Collection<Sinistre> tt_nonEpave() {
        return Sin_repo.findAll().stream().filter(this::non_Epave).collect(Collectors.toList());
    }


}
