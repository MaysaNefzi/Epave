package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.ERole;
import com.pfe.star.epave.Models.Epaviste;
import com.pfe.star.epave.Models.Role;
import com.pfe.star.epave.Repositories.EpavisteRepository;
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
@RequestMapping("/epaviste")
public class EpavisteController {
    private final Logger log = LoggerFactory.getLogger(EpavisteController.class);
    @Autowired
    MailSenderService mailSender;
    @Autowired
    GeneratorService generator;
    @Autowired
    private final EpavisteRepository Epav_repo;
    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;
    public EpavisteController(EpavisteRepository epav_repo){
        Epav_repo=epav_repo;
    }
    @Autowired
    RoleRepository roleRepository;
    @GetMapping("/liste_epav")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Epaviste> liste_epav(){
        return Epav_repo.getAllEpv();
    }

    @GetMapping("/epav_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> epav_ById(@PathVariable("id") Long id) {
        Optional<Epaviste> epav = Epav_repo.findById(id);
        return epav.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/epav_ByMatr_fisc/{matFisc}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Epaviste> epav_ByMatr_fisc(@PathVariable String matFisc) {
        return Epav_repo.findAll().stream().filter(x -> x.getMatriculeFiscale().equals(matFisc)).collect(Collectors.toList());

    }
    @GetMapping("/epav_ByCin/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Epaviste> epavByCin(@PathVariable String cin) {
        return Epav_repo.findAll().stream().filter(x -> x.getMatriculeFiscale().equals(cin)).collect(Collectors.toList());

    }

    @PostMapping("/ajouter_epav")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> ajouter_epav(@Valid @RequestBody Epaviste epav) throws URISyntaxException {
        if (Epav_repo.existsByUsername(epav.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email est déjà utilisé"));
        }
        log.info("Ajouter un nouveau Epaviste", epav);
        String pwd=generator.Pwdgenerator();
        epav.setPassword(bCryptPasswordEncoder.encode(pwd));
        Set<Role> roles = new HashSet<>();
        Role epavRole = roleRepository.findByName(ERole.ROLE_EPV)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(epavRole);
        epav.setRoles(roles);
        Epaviste result = Epav_repo.save(epav);
        mailSender.sendEmail(epav.getUsername(),"Mot de passe","Bonjour Mme/Mr "+epav.getNom()
                +"\n \n L'équipe STAR est heureuse de vous voir parmi elle. \n Vous trouvez ci-dessous votre mot de passe ,vous pouvez le changer quand vous voulez\n \n"
                +pwd
                +"\n Vous etes inscrit comme etant un epaviste"
                +"\n \n Merci");
        return ResponseEntity.created(new URI("/ajouter_epav" + result.getCin())).body(result);
    }

    @PutMapping("/modifier_epav/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Epaviste> modifier_epav(@Valid @RequestBody Epaviste epav, @PathVariable("id") long id) {
        log.info("Modifier Epaviste", epav);
        Optional<Epaviste> epavOptional = Epav_repo.findById(id);
        if (epavOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Epaviste e = epavOptional.get();
        e.setId(id);
        e.setCin(epav.getCin());
        e.setMatriculeFiscale(epav.getMatriculeFiscale());
        e.setUsername(epav.getUsername());
        e.setNom(epav.getNom());
        e.setPrenom(epav.getPrenom());
        e.setTel(epav.getTel());
        Set<Role> roles = new HashSet<>();
        Role epavRole = roleRepository.findByName(ERole.ROLE_EPV)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(epavRole);
        Epaviste result= Epav_repo.save(e);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/supprimer_epav/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_epav(@PathVariable Long id) {
        Epaviste e = null;
        try {
            e = Epav_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Epaviste introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        Epav_repo.delete(e);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Epaviste supprimé avec succes", Boolean.TRUE);
        return response;
    }
}
