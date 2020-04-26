package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.ERole;
import com.pfe.star.epave.Models.Role;
import com.pfe.star.epave.Models.Utilisateur;
import com.pfe.star.epave.Repositories.RoleRepository;
import com.pfe.star.epave.Repositories.UtilisateurRepository;
import com.pfe.star.epave.Security.Payload.Request.SignupRequest;
import com.pfe.star.epave.Security.Payload.Response.MessageResponse;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UtilisateurController {
    private final Logger log = LoggerFactory.getLogger(UtilisateurController.class);
    @Autowired
    private final UtilisateurRepository U_repo;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;

    public UtilisateurController(UtilisateurRepository u_repo){
        U_repo = u_repo;
    }
    @GetMapping("/liste_user")
    @CrossOrigin(origins = "*")
    public Collection<Utilisateur> liste_user(){
        return U_repo.findAll();
    }
    @GetMapping("/user_ById/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> user_ById(@PathVariable("id") Long id) {
        Optional<Utilisateur> utilisateur = U_repo.findById(id);
        return utilisateur.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
   /* @GetMapping("/user_ByNom/{username}")
    @CrossOrigin(origins = "*")
    public Collection<Utilisateur> user_ByNom(@PathVariable String username) {
        return U_repo.findAll().stream().filter(x -> x.getUsername().equals(username)).collect(Collectors.toList());
    }*/
    @GetMapping("/user_ByCin/{cin}")
    @CrossOrigin(origins = "*")
    public Collection<Utilisateur> user_ByCin(@PathVariable String cin) {
        return U_repo.findAll().stream().filter(x -> x.getCin().equals(cin)).collect(Collectors.toList());
    }

    @GetMapping("/user_ByEmail/{email}")
    @CrossOrigin(origins = "*")
    public Collection<Utilisateur> user_ByEmail(@PathVariable String email) {
        return U_repo.findAll().stream().filter(x -> x.getEmail().equals(email)).collect(Collectors.toList());
    }
    /*@PostMapping("/ajouter_user")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Utilisateur> ajouter_user(@Valid @RequestBody Utilisateur utilisateur) throws URISyntaxException {
        log.info("Ajouter un nouveau Utilisateur", utilisateur);
        String hashPW=encoder.encode(utilisateur.getPassword());
        utilisateur.setPassword(hashPW);

        Utilisateur result = U_repo.save(utilisateur);
        return ResponseEntity.created(new URI("/ajouter_utilisateur" + result.getCin())).body(result);
    }*/
    @PostMapping("/ajouter_user")
    public ResponseEntity<?> ajouter_user(@Valid @RequestBody SignupRequest signUpRequest) {

        if (U_repo.existsByUsername(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }
        if (U_repo.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }
        Utilisateur utilisateur = new Utilisateur(signUpRequest.getCin(),
                signUpRequest.getUsername(),
                encoder.encode((signUpRequest.getPassword())),
                signUpRequest.getNom(),
                signUpRequest.getPrenom(),
                signUpRequest.getEmail());
        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        if (strRoles != null){

            strRoles.forEach(role -> {
                switch (role) {
                    case "gestionnaire":
                        Role gestRole = roleRepository.findByName(ERole.ROLE_GEST)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(gestRole);
                        utilisateur.setRoles(roles);
                        U_repo.save(utilisateur);
                        break;
                    case "admin":
                        Role adminRole = roleRepository.findByName(ERole.ROLE_ADMIN)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        utilisateur.setRoles(roles);
                        U_repo.save(utilisateur);

                        break;
                    case "epaviste":
                        Role epvRole = roleRepository.findByName(ERole.ROLE_EPV)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(epvRole);
                        utilisateur.setRoles(roles);
                        U_repo.save(utilisateur);

                        break;
                    case "expert":
                        Role expRole = roleRepository.findByName(ERole.ROLE_EXP)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(expRole);
                        utilisateur.setRoles(roles);
                        U_repo.save(utilisateur);
                        break;
                    case "client":
                        Role cRole = roleRepository.findByName(ERole.ROLE_CLT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(cRole);
                        utilisateur.setRoles(roles);
                        U_repo.save(utilisateur);
                        break;
                }
            });
        }
        return ResponseEntity.ok().body(utilisateur);
    }
    @PutMapping("/modifier_user/{id}")
    @CrossOrigin(origins = "*")
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
       // u.setUsername(utilisateur.getUsername());
        String hashPW=encoder.encode(utilisateur.getPassword());
        u.setPassword(hashPW);
        u.setEmail(utilisateur.getEmail());
        u.setRoles(utilisateur.getRoles());
        Utilisateur result= U_repo.save(u);
        return ResponseEntity.ok().body(result);
    }
    @PutMapping("/modifier_password/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<Utilisateur> modifier_password(@Valid @RequestBody Utilisateur utilisateur, @PathVariable("id") long id) {
        log.info("Modifier Password", utilisateur);
        Optional<Utilisateur> userOptional = U_repo.findById(id);
        if (userOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Utilisateur u = userOptional.get();
        String hashPW=encoder.encode(utilisateur.getPassword());
        u.setPassword(hashPW);
        Utilisateur result= U_repo.save(u);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_user/{id}")
    @CrossOrigin(origins = "*")
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
