package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Epaviste;
import com.pfe.star.epave.Repositories.EpavisteRepository;
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
@RequestMapping("/epaviste")
public class EpavisteController {
    private final Logger log = LoggerFactory.getLogger(EpavisteController.class);
    @Autowired
    private final EpavisteRepository Epav_repo;
    public EpavisteController(EpavisteRepository epav_repo){
        Epav_repo=epav_repo;
    }

    @GetMapping("/liste_epav")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Epaviste> liste_epav(){
        return Epav_repo.findAll();
    }

    @GetMapping("/epavisteByCin/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> epav_ById(@PathVariable("cin") Long cin) {
        Optional<Epaviste> epav = Epav_repo.findById(cin);
        return epav.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/epavisteByMatr_fisc/{matFisc}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Epaviste> epavByMatr_fisc(@PathVariable String matFisc) {
        return Epav_repo.findAll().stream().filter(x -> x.getMatriculeFiscale().equals(matFisc)).collect(Collectors.toList());

    }

    @PostMapping("/ajouter_epav")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Epaviste> ajouter_epav(@Valid @RequestBody Epaviste epav) throws URISyntaxException {
        log.info("Ajouter un nouveau Epaviste", epav);
        Epaviste result = Epav_repo.save(epav);
        return ResponseEntity.created(new URI("/ajouter_epav" + result.getCin())).body(result);
    }

    @PutMapping("/modifier_epav/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Epaviste> modifier_epav(@Valid @RequestBody Epaviste epav, @PathVariable("cin") long cin) {
        log.info("Modifier Epaviste", epav);
        Optional<Epaviste> epavOptional = Epav_repo.findById(cin);
        if (epavOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Epaviste e = epavOptional.get();
        e.setId(epav.getId());
        e.setCin(cin);
        e.setMatriculeFiscale(epav.getMatriculeFiscale());
        e.setUsername(epav.getUsername());
        e.setPassword(epav.getPassword());
        e.setNom(epav.getNom());
        e.setPrenom(epav.getPrenom());
        e.setCompteActif(epav.isCompteActif());
        Epaviste result= Epav_repo.save(e);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/supprimer_epav/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_epav(@PathVariable Long cin) {
        Epaviste e = null;
        try {
            e = Epav_repo.findById(cin)
                    .orElseThrow(() -> new NotFoundException("Epaviste introuvable pour cette cin: " + cin));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        Epav_repo.delete(e);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Epaviste supprimé avec succes", Boolean.TRUE);
        return response;
    }
}
