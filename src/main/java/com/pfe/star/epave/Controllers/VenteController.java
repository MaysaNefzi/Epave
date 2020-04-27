package com.pfe.star.epave.Controllers;


import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.Vente;
import com.pfe.star.epave.Repositories.OffreRepository;
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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vente")
public class VenteController {
    private final Logger log = LoggerFactory.getLogger(VenteController.class);
    @Autowired
    private final VenteRepository V_repo;
    private final OffreRepository O_repo;

    private boolean enchere= true;
    public VenteController(VenteRepository v_repo , OffreRepository o_repo){
        V_repo=v_repo;
        O_repo=o_repo;
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
    // all vente participée
    @GetMapping("/vente_ByEpv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_By_Epv(@PathVariable("id") Long id){
        return V_repo.Vente_By_Epv(id);
    }

    //all vente participee ecnhere
    @GetMapping("/vente_enchre_ByEpv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_enchre_By_Epv(@PathVariable("id") Long id){
        return V_repo.Vente_By_Epv(id).stream().filter(this::est_Enchere).collect(Collectors.toList());
    }

    // vente en cours participée
    @GetMapping("/vente_encours_By_Epv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_encours_By_Epv(@PathVariable("id") Long id){
        LocalDate d = LocalDate.now();
        return  V_repo.Vente_By_Epv(id).stream().filter(x -> x.getDateFin().isAfter(d)).collect(Collectors.toList());
    }

    // vente encours non participée
    @GetMapping("/vente_encours_N_ByEpv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_encours_N_By_Epv(@PathVariable("id") Long id){
        List<Vente> l2 = Vente_By_Epv(id);
        Collection<Vente> l1 = liste_vente();
        List<Vente> diff = l1.stream()
                .filter(i -> !l2.contains(i))
                .collect (Collectors.toList());
        LocalDate d = LocalDate.now();
        return diff.stream().filter(x -> x.getDateFin().isAfter(d)).collect(Collectors.toList());
    }

    // all vente termine et participee
    @GetMapping("/vente_terminee_By_Epv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_terminee_By_Epv(@PathVariable("id") Long id){
        LocalDate d = LocalDate.now();
        return  V_repo.Vente_By_Epv(id).stream().filter(x -> x.getDateFin().isBefore(d)).collect(Collectors.toList());
    }

    //

    @GetMapping("/vente_Encours")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_Encours(){
        LocalDate d = LocalDate.now();
        return V_repo.findAllByDateFin().stream().filter(x -> x.getDateFin().isAfter(d)).collect(Collectors.toList());

    }

    /*public Collection<Vente> vente_ByEpv(@PathVariable("idE") Long idE ) {
        Collection<Offre> o = O_repo.OffreByEpv(idE);
    }*/

    @PostMapping("/ajouter_vente")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Vente> ajouter_vente(@Valid @RequestBody Vente vente) throws URISyntaxException {
        log.info("Ajouter une nouvelle vente", vente);
        Vente result = V_repo.save(vente);
        return ResponseEntity.created(new URI("/ajouter_vente" + result.getId())).body(result);
    }
    private boolean est_Enchere(Vente sin) {
        return sin.getEnchere().equals(enchere);
    }
    private boolean est_AppelOffre(Vente sin){return sin.getEnchere().equals(!enchere);
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
    public boolean traitement_encours(Vente v){
        LocalDate now = LocalDate.now();
        int x =v.getDateFin().compareTo(now);
        if(x>0){
            System.out.println("ss   : "+x);
            return false;
        }
        else {
            System.out.println("mm   : "+x);
            return true;
        }
    }

    @GetMapping("/traitement")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> traitement() {
        return V_repo.findAll().stream().filter(this::traitement_encours).collect(Collectors.toList());
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
        v.setDateDebut(vente.getDateDebut());
        v.setDateFin(vente.getDateFin());
        v.setDescription(vente.getDescription());
        v.setDuree(vente.getDuree());
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
