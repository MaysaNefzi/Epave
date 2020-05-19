package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.ERole;
import com.pfe.star.epave.Models.Expert;
import com.pfe.star.epave.Models.Role;
import com.pfe.star.epave.Repositories.ExpertRepository;
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
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final Logger log = LoggerFactory.getLogger(ExpertController.class);
    @Autowired
    MailSenderService mailSender;
    @Autowired
    GeneratorService generator;
    @Autowired
    private final ExpertRepository Exp_repo;
    public ExpertController(ExpertRepository exp_repo){
        Exp_repo=exp_repo;
    }
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    @Autowired
    RoleRepository roleRepository;

    @GetMapping("/liste_exp")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Expert> liste_exp(){
        return Exp_repo.getAllExp();
    }

    @GetMapping("/exp_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> exp_ById(@PathVariable("id") Long id) {
        Optional<Expert> exp = Exp_repo.findById(id);
        return exp.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/exp_ByCin/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Expert> exp_ByCin(@PathVariable String cin) {
        return Exp_repo.findAll().stream().filter(x -> x.getCin().equals(cin)).collect(Collectors.toList());
    }


    @PostMapping("/ajouter_exp")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> ajouter_exp(@Valid @RequestBody Expert exp) throws URISyntaxException {
        if (Exp_repo.existsByUsername(exp.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email est déjà utilisé"));
        }
        log.info("Ajouter un nouveau Expert", exp);
        Set<Role> roles = new HashSet<>();
        String pwd=generator.Pwdgenerator();
        exp.setPassword(bCryptPasswordEncoder.encode(pwd));
        Role expRole = roleRepository.findByName(ERole.ROLE_EXP)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(expRole);
        exp.setRoles(roles);
        Expert result = Exp_repo.save(exp);
        mailSender.sendEmail(exp.getUsername(),"Mot de passe","Bonjour Mme/Mr "+exp.getNom()
                +"\n \n L'équipe STAR est heureuse de vous voir parmi elle. \n Vous trouvez ci-dessous votre mot de passe ,vous pouvez le changer quand vous voulez\n \n"
                +pwd
                +"\n Vous etes inscrit comme etant un expert"
        +"\n \n Merci");
        return ResponseEntity.created(new URI("/ajouter_Exp" + result.getCin())).body(result);
    }

    @PutMapping("/modifier_exp/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Expert> modifier_exp(@Valid @RequestBody Expert exp, @PathVariable("id") long id) {
        log.info("Modifier Expert", exp);
        Optional<Expert> expOptional = Exp_repo.findById(id);
        if (expOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Expert e = expOptional.get();
        e.setId(id);
        e.setCin(exp.getCin());
        e.setNom(exp.getNom());
        e.setPrenom(exp.getPrenom());
        e.setUsername(exp.getUsername());
        e.setTel(exp.getTel());
        Expert result= Exp_repo.save(e);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/supprimer_exp/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_exp(@PathVariable Long id) {
        Expert e = null;
        try {
            e = Exp_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Expert introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        Exp_repo.delete(e);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Expert supprimé avec succes", Boolean.TRUE);
        return response;
    }


}
