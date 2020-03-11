package com.pfe.star.epave.Controllers;


import com.pfe.star.epave.Models.Vente;
import com.pfe.star.epave.Repositories.VenteRepository;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vente")
public class VenteController {
    private final Logger log = LoggerFactory.getLogger(VenteController.class);
    @Autowired
    private final VenteRepository V_repo;
    private boolean enchere= true;
    public VenteController(VenteRepository v_repo){
        V_repo=v_repo;
    }

    @GetMapping("/liste_vente")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> liste_vente(){
        return V_repo.findAll();
    }

    @GetMapping("/vente_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> vente_ById(@PathVariable("id") Long id) {
        Optional<Vente> vente = V_repo.findById(id);
        return vente.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/ajouter_vente")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Vente> ajouter_vente(@Valid @RequestBody Vente vente) throws URISyntaxException {
        log.info("Ajouter une nouvelle vente", vente);
        Vente result = V_repo.save(vente);
        return ResponseEntity.created(new URI("/ajouter_vente" + result.getId())).body(result);
    }
    private boolean est_Enchere(Vente sin) {
        return sin.getEpave().equals(enchere);
    }
    private boolean est_AppelOffre(Vente sin){
        return sin.getEpave().equals(!enchere);
    }

    @GetMapping("/liste_enchere")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> liste_enchere() {
        return V_repo.findAll().stream().filter(this::est_Enchere).collect(Collectors.toList());
    }
    @GetMapping("/liste_appelOffre")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> liste_appelOffre() {
        return V_repo.findAll().stream().filter(this::est_AppelOffre).collect(Collectors.toList());
    }

    @PutMapping("/modifier_vente/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Vente> modifier_vente(@Valid @RequestBody Vente vente, @PathVariable("id") long id) {
        log.info("Modifier vente", vente);
        Optional<Vente> venteOptional = V_repo.findById(id);
        if (venteOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Vente v = venteOptional.get();
        v.setId(id);
        v.setNumeroSinistre(vente.getNumeroSinistre());
        v.setImmatriculation(vente.getImmatriculation());
        v.setDateAccident(vente.getDateAccident());
        v.setNumChassis(vente.getNumChassis());
        v.setMarque(vente.getMarque());
        v.setEpave(vente.getEpave());
        v.setModele(vente.getModele());
        v.setValeurVenale(vente.getValeurVenale());
        v.setDateDebut(vente.getDateDebut());
        v.setDateFin(vente.getDateFin());
        v.setDescription(vente.getDescription());
        v.setEnchere(vente.getEnchere());
        v.setPrixDebut(vente.getPrixDebut());
        Vente result= V_repo.save(v);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/supprimer_vente/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_vente(@PathVariable Long id) {
        Vente v = null;
        try {
            v = V_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Vente introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        V_repo.delete(v);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Vente supprimée avec succes", Boolean.TRUE);
        return response;
    }
}
