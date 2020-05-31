package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.ERole;
import com.pfe.star.epave.Models.Gestionnaire;
import com.pfe.star.epave.Models.Role;
import com.pfe.star.epave.Repositories.GestionnaireRepository;
import com.pfe.star.epave.Repositories.RoleRepository;
import com.pfe.star.epave.Security.Payload.Response.MessageResponse;
import com.pfe.star.epave.Security.Services.MailSenderService;
import com.pfe.star.epave.Security.Services.GeneratorService;
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
@RequestMapping("/gestionnaire")
public class GestionnaireController {
    private final Logger log = LoggerFactory.getLogger(GestionnaireController.class);
    @Autowired
    MailSenderService mailSender;
    @Autowired
    GeneratorService generator;
    @Autowired
    private final GestionnaireRepository Gest_repo;
    @Autowired
    PasswordEncoder encoder;
    public GestionnaireController(GestionnaireRepository gest_repo){
        Gest_repo=gest_repo;
    }
    @Autowired
    RoleRepository roleRepository;

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
    public ResponseEntity<?> ajouter_gest(@Valid @RequestBody Gestionnaire gest) throws URISyntaxException {
        Set<Role> roles = new HashSet<>();
        if (Gest_repo.existsByUsername(gest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email est déjà utilisé"));
        }
        log.info("Ajouter un nouveau gestionnaire", gest);
        String pwd=generator.Pwdgenerator();
        gest.setPassword(encoder.encode(pwd));
        Role gestRole = roleRepository.findByName(ERole.ROLE_GEST)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(gestRole);
        gest.setRoles(roles);
        Gestionnaire result = Gest_repo.save(gest);
        mailSender.sendEmail(gest.getUsername(),"Mot de passe","Bonjour Mme/Mr "+gest.getNom()
                +"\n \n L'équipe STAR est heureuse de vous voir parmi elle. \n Vous trouvez ci-dessous votre mot de passe ,vous pouvez le changer quand vous voulez\n \n"
                +pwd
                +"\n Vous etes inscrit comme etant un gestionnaire"
                +"\n \n Merci");
        return ResponseEntity.created(new URI("/ajouter_gest" + result.getCin())).body(result);
    }
    @PostMapping("/ajouter_admin")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> ajouter_admin(@Valid @RequestBody Gestionnaire gest) throws URISyntaxException {
        Set<Role> roles = new HashSet<>();
        if (Gest_repo.existsByUsername(gest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email est déjà utilisé"));
        }
        log.info("Ajouter un nouveau gestionnaire", gest);
        String pwd=generator.Pwdgenerator();
        gest.setPassword(encoder.encode(pwd));
        Role adminRole=roleRepository.findByName(ERole.ROLE_ADMIN)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        Role gestRole = roleRepository.findByName(ERole.ROLE_GEST)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(gestRole);
        roles.add(adminRole);
        gest.setRoles(roles);
        Gestionnaire result = Gest_repo.save(gest);
        mailSender.sendEmail(gest.getUsername(),"Mot de passe","Bonjour Mme/Mr "+gest.getNom()
                +"\n \n L'équipe STAR est heureuse de vous voir parmi elle. \n Vous trouvez ci-dessous votre mot de passe ,vous pouvez le changer quand vous voulez\n \n"
                +pwd
                +"\n Vous etes inscrit comme etant un gestionnaire admin"
                +"\n \n Merci");
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
        g.setMatricule(gest.getMatricule());
        g.setNom(gest.getNom());
        g.setPrenom(gest.getPrenom());
        g.setUsername(gest.getUsername());
        Set<Role> roles = new HashSet<>();
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
