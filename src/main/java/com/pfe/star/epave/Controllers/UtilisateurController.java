package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Utilisateur;
import com.pfe.star.epave.Repositories.UtilisateurRepository;
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
@RequestMapping("/utilisateur")
public class UtilisateurController {
    private final Logger log = LoggerFactory.getLogger(UtilisateurController.class);
    @Autowired
    private final UtilisateurRepository U_repo;
    public UtilisateurController(UtilisateurRepository u_repo){
        U_repo = u_repo;
    }
    @GetMapping("/liste_user")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Utilisateur> liste_user(){
        return U_repo.findAll();
    }
    @GetMapping("/user_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> user_ById(@PathVariable("id") Long id) {
        Optional<Utilisateur> utilisateur = U_repo.findById(id);
        return utilisateur.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/user_ByNom/{username}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Utilisateur> user_ByNom(@PathVariable String username) {
        return U_repo.findAll().stream().filter(x -> x.getUsername().equals(username)).collect(Collectors.toList());

    }
    @GetMapping("/user_ByCin/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Utilisateur> user_ByCin(@PathVariable String cin) {
        return U_repo.findAll().stream().filter(x -> x.getCin().equals(cin)).collect(Collectors.toList());

    }
    @PostMapping("/ajouter_user")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Utilisateur> ajouter_user(@Valid @RequestBody Utilisateur utilisateur) throws URISyntaxException {
        log.info("Ajouter un nouveau Utilisateur", utilisateur);
        Utilisateur result = U_repo.save(utilisateur);
        return ResponseEntity.created(new URI("/ajouter_utilisateur" + result.getCin())).body(result);
    }
    @PutMapping("/modifier_user/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Utilisateur> modifier_user(@Valid @RequestBody Utilisateur utilisateur, @PathVariable("id") long id) {
        log.info("Modifier Utilisateur", utilisateur);
        Optional<Utilisateur> userOptional = U_repo.findById(id);
        if (userOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Utilisateur u = userOptional.get();
        u.setCin(utilisateur.getCin());
        u.setId(id);
        u.setNom(utilisateur.getNom());
        u.setPrenom(utilisateur.getPrenom());
        u.setUsername(utilisateur.getUsername());
        u.setPassword(utilisateur.getPassword());
        u.setRole(utilisateur.getRole());
       // u.setEmail(utilisateur.getEmail());
        Utilisateur result= U_repo.save(u);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_user/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_user(@PathVariable Long id) {
        Utilisateur u = null;
        try {
            u = U_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("utilisteur introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        U_repo.delete(u);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Utilisateur supprimé avec succes", Boolean.TRUE);
        return response;
    }
}
