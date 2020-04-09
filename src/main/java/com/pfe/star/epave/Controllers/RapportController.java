package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Rapport;
import com.pfe.star.epave.Repositories.RapportRepository;
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


@RestController
@RequestMapping("/rapport_pre")
public class RapportController {
    private final Logger log = LoggerFactory.getLogger(RapportController.class);

    @Autowired
    private final RapportRepository R_repo;
    public RapportController(RapportRepository r_repo ){
        R_repo=r_repo;
    }
    @GetMapping("/liste_rapport_pre")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Rapport> liste_rapport_pre(){
        return R_repo.findAll();
    }
    @GetMapping("/rapport_pre_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> rapport_ById(@PathVariable Long id){
        Optional<Rapport> rapport = R_repo.findById(id);
        return rapport.map(response-> ResponseEntity.ok().body(response))
                .orElse((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
    /*@GetMapping("/rapport_ByNumSinistre/{mat}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Rapport> rapport_ByNumSinistre(@PathVariable String sin) {
        return R_repo.findAll().stream().filter(x -> x.getSinistre().equals(sin)).collect(Collectors.toList());

    }*/
    @PostMapping("/ajouter_rapport_pre")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Rapport> ajouter_rapport_pre(@Valid @RequestBody Rapport rap) throws URISyntaxException {
        log.info("Ajouter un nouveau rapport preliminaire", rap);
        Rapport result = R_repo.save(rap);
        return ResponseEntity.created(new URI("/ajouter_rapport_pre" + result.getId())).body(result);
    }
    @PutMapping("/modifier_rapport_pre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Rapport>modifier_rapport_pre(@Valid @RequestBody Rapport rap,@PathVariable("id") long id){
        log.info("Modifier Un rapport preliminaire", rap);
        Optional<Rapport> rapportOptional= R_repo.findById(id);
        if (rapportOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Rapport r= rapportOptional.get();
        r.setId(id);
        //r.setSinistre(rap.getSinistre());
        r.setValeurVenale(rap.getValeurVenale());
        r.setDegatsConstates(rap.getDegatsConstates());
        r.setEstimationValeurEpave(rap.getEstimationValeurEpave());
        r.setLieuVehicule(rap.getLieuVehicule());
        Rapport result = R_repo.save(r);
        return  ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_rapport_pre/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_rapport_pre(@PathVariable Long id) {
        Rapport r = null;
        try {
            r = R_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Rapport introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        R_repo.delete(r);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Rapport supprimé avec succes", Boolean.TRUE);
        return response;
    }




}
