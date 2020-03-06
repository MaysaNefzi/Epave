package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.RapportFinal;
import com.pfe.star.epave.Repositories.RapportFinalRepository;
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
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/RapportFinal")
public class RapportFinalController {
    private final Logger log = LoggerFactory.getLogger(RapportFinalController.class);

    @Autowired
    private final RapportFinalRepository RF_repo;
    public RapportFinalController(RapportFinalRepository r_repo ){
        RF_repo=r_repo;
    }
    @GetMapping("/liste_rapport_final")
    public Collection<RapportFinal> liste_rapport_final(){
        return RF_repo.findAll();
    }
    @GetMapping("/rapportfinal/{sin}")
    public ResponseEntity<?> rapport_Final_ByNumSinistre(@PathVariable Long sin){
        Optional<RapportFinal> rapportf = RF_repo.findById(sin);
        return rapportf.map(response-> ResponseEntity.ok().body(response))
                .orElse((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
    @PostMapping("/ajouter_rapport_final")
    public ResponseEntity<RapportFinal> ajouter_rapport_final(@Valid @RequestBody RapportFinal rapF) throws URISyntaxException {
        log.info("Ajouter un nouveau rapport final", rapF);
        RapportFinal result = RF_repo.save(rapF);
        return ResponseEntity.created(new URI("/ajouter_rapport_final" + result.getId())).body(result);
    }
    @PutMapping("/modifier_rapport_final/{id}")
    public ResponseEntity<RapportFinal>modifier_rapport_final(@Valid @RequestBody RapportFinal rapF,@PathVariable("id") long id){
        log.info("Modifier Un rapport final", rapF);
        Optional<RapportFinal> rapportFOptional= RF_repo.findById(id);
        if (rapportFOptional.isEmpty())
            return ResponseEntity.notFound().build();
        RapportFinal rf= rapportFOptional.get();
        rf.setId(id);
        rf.setSinistre(rapF.getSinistre());
        rf.setValeurVenale(rapF.getValeurVenale());
        rf.setDegatsConstates(rapF.getDegatsConstates());
        rf.setEstimationValeurEpave(rapF.getEstimationValeurEpave());
        rf.setLieuVehicule(rapF.getLieuVehicule());
        RapportFinal result = RF_repo.save(rf);
        return  ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_rapport_final/{id}")
    public Map<String, Boolean> supprimer_rapport_final(@PathVariable Long id) {
        RapportFinal rf = null;
        try {
            rf = RF_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Rapport introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        RF_repo.delete(rf);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Rapport supprimé avec succes", Boolean.TRUE);
        return response;
    }
}
