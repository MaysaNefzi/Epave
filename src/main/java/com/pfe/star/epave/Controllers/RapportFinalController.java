package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Police;
import com.pfe.star.epave.Models.RapportPre;
import com.pfe.star.epave.Models.RapportFinal;
import com.pfe.star.epave.Repositories.PoliceRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/RapportFinal")
public class RapportFinalController {
    private final Logger log = LoggerFactory.getLogger(RapportFinalController.class);

    @Autowired
    private final RapportFinalRepository RF_repo;
    private final PoliceController P_cntrl;

    public RapportFinalController(RapportFinalRepository r_repo , PoliceController p_cntrl){
        RF_repo=r_repo;
        P_cntrl=p_cntrl;
    }
    @GetMapping("/liste_rapport_final")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<RapportFinal> liste_rapport_final(){
        return RF_repo.findAll();
    }
    @GetMapping("/rapport_final_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> rapport_final_ById(@PathVariable Long id){
        Optional<RapportFinal> rapportf = RF_repo.findById(id);
        return rapportf.map(response-> ResponseEntity.ok().body(response))
                .orElse((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }
    @GetMapping("/rapport_final_BySinId/{sin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<RapportFinal> rapport_ByNumSinistre(@PathVariable Long sin) {
        return RF_repo.findAll().stream().filter(x -> x.getSinistre().getId().equals(sin)).collect(Collectors.toList());

    }
    //    @GetMapping("/rapport_final_ByEpv/{idC}")
    //    @CrossOrigin(origins = "http://localhost:4200")
    //    public List<RapportFinal> rapport_final_ByEpv(@PathVariable Long idC) {
    //       // List<Police> polices = P_cntrl.police_ByClient(idC).toArray();
    //
    //    }
    @PostMapping("/ajouter_rapport_final")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<RapportFinal> ajouter_rapport_final(@Valid @RequestBody RapportFinal rapF) throws URISyntaxException {
        log.info("Ajouter un nouveau rapport final", rapF);
        RapportFinal result = RF_repo.save(rapF);
        return ResponseEntity.created(new URI("/ajouter_rapport_final" + result.getId())).body(result);
    }
  /*  @PutMapping("/modifier_rapport_final/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
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
    }*/
    @DeleteMapping("/supprimer_rapport_final/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
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
