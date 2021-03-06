package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Utilisateur;
import com.pfe.star.epave.Repositories.RoleRepository;
import com.pfe.star.epave.Repositories.UtilisateurRepository;
import com.pfe.star.epave.Security.Payload.Request.ChangePWDRequest;

import com.pfe.star.epave.Security.Payload.Request.ResetPWDRequest;
import com.pfe.star.epave.Security.Payload.Response.MessageResponse;
import com.pfe.star.epave.Security.Services.GeneratorService;
import com.pfe.star.epave.Security.Services.MailSenderService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/user")
public class UtilisateurController {
    private final Logger log = LoggerFactory.getLogger(UtilisateurController.class);
    @Autowired
    private final UtilisateurRepository U_repo;
    @Autowired
    MailSenderService mailSender;
    @Autowired
    GeneratorService generator;
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
    @GetMapping("/user_ByNom/{username}")
    @CrossOrigin(origins = "*")
    public Collection<Utilisateur> user_ByNom(@PathVariable String username) {
        return U_repo.findAll().stream().filter(x -> x.getUsername().equals(username)).collect(Collectors.toList());
    }
    @GetMapping("/user_ByCin/{cin}")
    @CrossOrigin(origins = "*")
    public Collection<Utilisateur> user_ByCin(@PathVariable String cin) {
        return U_repo.findAll().stream().filter(x -> x.getCin().equals(cin)).collect(Collectors.toList());
    }
    @GetMapping("/user_ByEmail/{email}")
    @CrossOrigin(origins = "*")
    public Collection<Utilisateur> user_ByEmail(@PathVariable String email) {
        return U_repo.findAll().stream().filter(x -> x.getUsername().equals(email)).collect(Collectors.toList());
    }
    @PutMapping("/modifier_password/{id}")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> modifier_password(@Valid @RequestBody ChangePWDRequest changePWDRequest, @PathVariable("id") long id) {
        Optional<Utilisateur> userOptional = U_repo.findById(id);
        if (userOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Utilisateur u = userOptional.get();
        String old_entred=changePWDRequest.getOldPassword();
        String old_db= u.getPassword();
        String entred= changePWDRequest.getNewPassword();
        if (! encoder.matches(old_entred,old_db)){
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Ancien mot de passe est incorrect"));}
        else{
            u.setPassword(encoder.encode(entred));
             U_repo.save(u);
            return ResponseEntity.ok().body(new MessageResponse("Mot de passe changé avec succés"));
        }
    }
    @PutMapping("/reset_password")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> reset_password(@Valid @RequestBody ResetPWDRequest resetPWDRequest){
        Optional<Utilisateur> userOptional = U_repo.findByUsername(resetPWDRequest.getUsername());
        if (userOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Utilisateur u = userOptional.get();
        String pwd=generator.Pwdgenerator();
        u.setPassword(encoder.encode(pwd));
        mailSender.sendEmail(resetPWDRequest.getUsername(),"Récupérer mot de passe","Bonjour Mme/Mr "+u.getNom()
                +" \n Vous avez oublié votre mot de passe, la connexion est maintenant disponible avec  le nouveau mot de passe ci-dessous . \n"
                +"\n Vous pouvez le changer pour plus de sécurité. \n"
                +pwd
                +"\n \n Merci");
        U_repo.save(u);
        return ResponseEntity.ok().body(new MessageResponse("Mot de passe récupéré avec succés"));
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
