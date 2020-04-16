package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Gestionnaire;
import com.pfe.star.epave.Repositories.GestionnaireRepository;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public GestionnaireController(GestionnaireRepository gest_repo){
        Gest_repo=gest_repo;
    }

    @GetMapping("/liste_gest")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Gestionnaire> liste_gest(){
        return Gest_repo.getAllGest();
    }

    @GetMapping("/gest_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> gest_ById(@PathVariable("id") Long id) {
        Optional<Gestionnaire> gest = Gest_repo.findById(id);
        return gest.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/gest_ByMatr/{mat}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Gestionnaire> gest_ByMatr(@PathVariable String mat) {
        return Gest_repo.findAll().stream().filter(x -> x.getMatricule().equals(mat)).collect(Collectors.toList());

    }
    @GetMapping("/gest_Bycin/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Gestionnaire> gest_Bycin(@PathVariable String cin) {
        return Gest_repo.findAll().stream().filter(x -> x.getCin().equals(cin)).collect(Collectors.toList());

    }
    @PostMapping("/ajouter_gest")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Gestionnaire> ajouter_gest(@Valid @RequestBody Gestionnaire gest) throws URISyntaxException {
        log.info("Ajouter un nouveau gestionnaire", gest);
        String hashPW=bCryptPasswordEncoder.encode(gest.getPassword());
        gest.setPassword(hashPW);
        Gestionnaire result = Gest_repo.save(gest);
        return ResponseEntity.created(new URI("/ajouter_gest" + result.getCin())).body(result);
    }
    @PutMapping("/modifier_gest/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Gestionnaire> modifier_gest(@Valid @RequestBody Gestionnaire gest, @PathVariable("id") long id) {
        log.info("Modifier Gestionnaire", gest);
        Optional<Gestionnaire> gestOptional = Gest_repo.findById(id);
        if (gestOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Gestionnaire g = gestOptional.get();
        g.setCin(gest.getCin());
        g.setId(id);
        g.setMatricule(gest.getMatricule());
        g.setNom(gest.getNom());
        g.setPrenom(gest.getPrenom());
        g.setAdmin(gest.isAdmin());
        g.setUsername(gest.getUsername());
        g.setPassword(gest.getPassword());
        Gestionnaire result= Gest_repo.save(g);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_gest/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_gest(@PathVariable Long id) {
        Gestionnaire g = null;
        try {
            g = Gest_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Gestionaire introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        Gest_repo.delete(g);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Gestionnaire supprimé avec succes", Boolean.TRUE);
        return response;
    }



}
