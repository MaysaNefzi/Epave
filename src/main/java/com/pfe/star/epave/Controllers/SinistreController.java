package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Police;
import com.pfe.star.epave.Models.Sinistre;
import com.pfe.star.epave.Repositories.SinistreRepository;
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
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Sinistre> liste_sin(){
        return Sin_repo.findAll();
    }

    @GetMapping("/sin_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> sin_ByID(@PathVariable("id") Long id) {
        Optional<Sinistre> sin = Sin_repo.findById(id);
        return sin.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/sin_ByNumPolice/{numeroPolice}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Sinistre> sin_ByNumPolice(@PathVariable String numeroPolice) {
        return Sin_repo.findAll().stream().filter(x -> x.getPolice().getNumPolice().equals(numeroPolice)).collect(Collectors.toList());

    }
    @GetMapping("/sin_ByNumSinistre/{numeroSinistre}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Sinistre> sin_ByNumSinistre(@PathVariable String numeroSinistre) {
        return Sin_repo.findAll().stream().filter(x -> x.getNumeroSinistre().equals(numeroSinistre)).collect(Collectors.toList());

    }
    @PostMapping("/ajouter_sinistre")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Sinistre> ajouter_sinistre(@Valid @RequestBody Sinistre sini) throws URISyntaxException {
        log.info("add police", sini);
        Sinistre result = Sin_repo.save(sini);
        return ResponseEntity.created(new URI("/ajouter_police" + result.getNumeroSinistre())).body(result);
    }

    @GetMapping("/Sin_ByImmatriculation/{immatriculation}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Sinistre> Sin_ByImmatriculation(@PathVariable String immatriculation) {
        return Sin_repo.findAll().stream().filter(x -> x.getImmatriculation().equals(immatriculation)).collect(Collectors.toList());

    }
    private boolean est_Epave(Sinistre sin) {
        return sin.getEpave().equals(epave);
    }
    private boolean non_Epave(Sinistre sin){
        return sin.getEpave().equals(!epave);
    }

    @GetMapping("/liste_Epave")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Sinistre> tt_Epave() {
        return Sin_repo.findAll().stream().filter(this::est_Epave).collect(Collectors.toList());
    }
    @GetMapping("/liste_nonEpave")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Sinistre> tt_nonEpave() {
        return Sin_repo.findAll().stream().filter(this::non_Epave).collect(Collectors.toList());
    }
    @PutMapping("/modifier_sinistre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Sinistre> modifier_sinistre(@Valid @RequestBody Sinistre sinistre, @PathVariable("id") long id) {
        log.info("Modifier sinistre", sinistre);
        Optional<Sinistre> sinistreOptional = Sin_repo.findById(id);
        if (sinistreOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Sinistre sin = sinistreOptional.get();
        sin.setId(id);
        sin.setNumeroSinistre(sinistre.getNumeroSinistre());
        sin.setImmatriculation(sinistre.getImmatriculation());
        sin.setDateAccident(sinistre.getDateAccident());
        sin.setNumChassis(sinistre.getNumChassis());
        sin.setMarque(sinistre.getMarque());
        sin.setEpave(sinistre.getEpave());
        sin.setModele(sinistre.getModele());
        sin.setEtatSinistre(sinistre.getEtatSinistre());
        Sinistre result= Sin_repo.save(sin);
        return ResponseEntity.ok().body(result);
    }


}
