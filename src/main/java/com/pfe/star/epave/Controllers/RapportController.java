package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Epaviste;
import com.pfe.star.epave.Models.Rapport;
import com.pfe.star.epave.Repositories.RapportRepository;
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
@RequestMapping("/rapport")
public class RapportController {
    private final Logger log = LoggerFactory.getLogger(RapportController.class);

    @Autowired
    private final RapportRepository R_repo;
    public RapportController(RapportRepository r_repo ){
        R_repo=r_repo;
    }
    @GetMapping("/liste_rapport_pre")
    public Collection<Rapport> liste_rapport_pre(){
        return R_repo.findAll();
    }
    @GetMapping("/rapport/{sin}")
    public ResponseEntity<?> rapport_ByNumSinistre(@PathVariable Long sin){
        Optional<Rapport> rapport = R_repo.findById(sin);
        return rapport.map(response-> ResponseEntity.ok().body(response))
                .orElse((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
    @PostMapping("/ajouter_rapport_pre")
    public ResponseEntity<Rapport> ajouter_rapport_pre(@Valid @RequestBody Rapport rap) throws URISyntaxException {
        log.info("Ajouter un nouveau rapport preliminaire", rap);
        Rapport result = R_repo.save(rap);
        return ResponseEntity.created(new URI("/ajouter_rapport_pre" + result.getId())).body(result);
    }
    @PutMapping("/modifier_rapport_pre")
    public ResponseEntity<Rapport>modifier_rapport_pre(@Valid @RequestBody Rapport rap,@PathVariable("id") long id){
        log.info("Modifier Un rapport preliminaire", rap);
        Optional<Rapport> rapportOptional= R_repo.findById(id);
        if (rapportOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Rapport r= rapportOptional.get();
        r.setId(id);
        r.setSinistre(rap.getSinistre());
        r.setValeurVenale(rap.getValeurVenale());
        r.setDegatsConstates(rap.getDegatsConstates());
        r.setEstimationValeurEpave(rap.getEstimationValeurEpave());
        r.setLieuVehicule(rap.getLieuVehicule());
        Rapport result = R_repo.save(r);
        return  ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_rapport/{id}")
    public ResponseEntity<?>supprimer_rapport(@PathVariable("id")String id){
        log.info("Supprimer Rapport",id);
        List<Rapport> tt_rapports= R_repo.findAll().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        for (Rapport r: tt_rapports)
            R_repo.deleteById((r.getId()));
        return ResponseEntity.ok().build();
    }




}
