package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.*;
import com.pfe.star.epave.Repositories.ClientRepository;
import com.pfe.star.epave.Repositories.ConfirmationTokenRepository;
import com.pfe.star.epave.Repositories.RoleRepository;
import com.pfe.star.epave.Security.Payload.Request.ConfirmationRequest;
import com.pfe.star.epave.Security.Payload.Request.ResetPWDRequest;
import com.pfe.star.epave.Security.Payload.Request.SignupRequest;
import com.pfe.star.epave.Security.Payload.Response.MessageResponse;
import com.pfe.star.epave.Security.Services.GeneratorService;
import com.pfe.star.epave.Security.Services.MailSenderService;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.view.RedirectView;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final Logger log = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private final ClientRepository C_repo;
    @Autowired
    ConfirmationTokenRepository Confirm_Repo;
    @Autowired
    MailSenderService mailSender;
    @Autowired
    GeneratorService generator;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    RoleRepository roleRepository;
    public final static String LOGIN_URL= "/authentif/signin";


    public ClientController(ClientRepository c_repo){
        C_repo = c_repo;
    }
    @GetMapping("/liste_client")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Client> liste_client(){
        return C_repo.findAll();
    }

    @GetMapping("/client_ByPolice/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Long[] client_ByPolice(@PathVariable Long id){
        p_c c = (p_c)C_repo.clientpolice(id).toArray()[0];
        Long L[]={c.getTel(),c.getTel2()};
        log.info("objet pc1 : " + L[0]+"   pc2 "+ L[1]);
        return L;
    }

    @GetMapping("/client_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> client_ById(@PathVariable("id") Long id) {
        Optional<Client> client = C_repo.findById(id);
        return client.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/client_ByNom/{nom}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Client> client_ByNom(@PathVariable String nom) {
        return C_repo.findAll().stream().filter(x -> x.getNom().equals(nom)).collect(Collectors.toList());

    }
    @GetMapping("/client_ByCin/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Client> client_ByCin(@PathVariable String cin) {
        return C_repo.findAll().stream().filter(x -> x.getCin().equals(cin)).collect(Collectors.toList());

    }
    @GetMapping("/client_ByEmail/{e}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Client> client_ByEmail(@PathVariable String e) {
        return C_repo.findAll().stream().filter(x -> x.getUsername().equals(e)).collect(Collectors.toList());

    }
    @PostMapping("/ajouter_compte_client")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> ajouter_compte_client(@Valid @RequestBody Client client) throws URISyntaxException {
        log.info("Créer un compte client", client);
        Set<Role> roles = new HashSet<>();
        Role cRole = roleRepository.findByName(ERole.ROLE_CLT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(cRole);
        client.setRoles(roles);
        Client result = C_repo.save(client);
        return ResponseEntity.created(new URI("/ajouter_compte_client" + result.getCin())).body(result);
    }

    @PutMapping("/signup")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> RegisterClient(@Valid @RequestBody SignupRequest signupRequest) {
       Optional<Client> client = C_repo.findByCin(signupRequest.getCin());
        if (client.isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Cin introuvable!"));
        Client c = client.get();
        if(!Confirm_Repo.tokenbyidClient(c.getId()).isEmpty())
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Vous avez un code deja"));
        if (c.isCompteActif())
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Vous avez un compte deja"));
//        c.setCin(c.getCin());
//        c.setId(c.getId());
//        c.setNom(c.getNom());
//        c.setPrenom(c.getPrenom());
//        c.setAdresse(c.getAdresse());
//        c.setDelegation(c.getDelegation());
//        c.setGouvernement(c.getGouvernement());
//        c.setTel1(c.getTel1());
//        c.setTel2(c.getTel2());
        Set<Role> roles = new HashSet<>();
        Role cRole = roleRepository.findByName(ERole.ROLE_CLT)
                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
        roles.add(cRole);
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setClient(c);
        Date creation =new Date();
        confirmationToken.setCreatedDate(creation);
        confirmationToken.setExpiredDate(new Date(creation.getTime()+(1000 * 60 * 60)));//expiration code apres 1h
        String code=generator.Codegenerator();
        confirmationToken.setConfirmationToken(code);
        confirmationToken.setClient(c);
        confirmationToken.setIdClient(c.getId());
        Confirm_Repo.save(confirmationToken);
        mailSender.sendEmail(signupRequest.getUsername(),"Code de Confirmation","Bonjour Mme/Mr "+c.getNom()
                +"\n \n L'équipe STAR est heureuse de vous voir parmi elle. \n Vous trouvez ci-dessous votre code de confirmation ,merci de confirmer votre inscription tout de suite\n \n"
                +code
                +"\n \n Merci");
        c.setUsername(signupRequest.getUsername());
        c.setPassword(encoder.encode(signupRequest.getPassword()));
        C_repo.save(c);
        return ResponseEntity.ok(new MessageResponse("En attente de confirmation"));
    }

    @PutMapping("/newCode")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> newCode(@Valid @RequestBody ResetPWDRequest r){
        Client client1 = C_repo.findByEmail(r.getUsername()).get();
    //    log.info("Id  :  "+client1.getId());
        ConfirmationToken confirmationToken;
        if(client1.getPassword()==null) //il n'est pas inscrit
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: non inscrit"));   // non innscrit return objet vide
        // inscrit : send new code
        Long idC = client1.getId();
        if(!Confirm_Repo.tokenbyidClient(idC).isEmpty()){//si code non expiré juste update
            confirmationToken= Confirm_Repo.tokenbyidClient(idC).get(); // old code
        }
        else { // code expiré : creat new code
            confirmationToken =new ConfirmationToken();
        }
        Date creation =new Date();
        confirmationToken.setCreatedDate(creation);
        confirmationToken.setExpiredDate(new Date(creation.getTime()+(1000 * 60 * 60)));//expiration code apres 1h
        String code=generator.Codegenerator();
        confirmationToken.setConfirmationToken(code);
        confirmationToken.setClient(client1);
        confirmationToken.setIdClient(client1.getId());

        Confirm_Repo.save(confirmationToken);
        mailSender.sendEmail(client1.getUsername(),"Code de Confirmation","Bonjour Mme/Mr "+client1.getNom()
                +"\n \n L'équipe STAR est heureuse de vous voir parmi elle. \n Vous trouvez ci-dessous votre code de confirmation ,merci de confirmer votre inscription tout de suite\n \n"
                +code
                +"\n \n Merci");
        return ResponseEntity.ok(new MessageResponse("Code envoyé"));
    }

    @PostMapping("/confirm-account")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> confirmation(@RequestBody ConfirmationRequest confirmationRequest){
        try {
            Date now = new Date();
            ConfirmationToken token = Confirm_Repo.findByConfirmationToken(confirmationRequest.getCode());
            Map<String, String> accountConfirmation = new HashMap<>();
            accountConfirmation.put("accountConfirmation", "true");
            Client client = C_repo.getById(token.getClient().getId());
            if (client.getUsername().compareTo(confirmationRequest.getUsername()) != 0) {
                accountConfirmation.put("accountConfirmation", "false");
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Code invalide"));
            }
            if (now.compareTo(token.getExpiredDate()) > 0) {
                accountConfirmation.put("accountConfirmation", "false");
                Confirm_Repo.deleteConfirmationToken(token.getIdClient());
                C_repo.deleteMailPassword(token.getIdClient(), null);
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Error: Code expiré"));
            }
            accountConfirmation.put("username", client.getUsername());
            client.setCompteActif(true);
            C_repo.save(client);
            Confirm_Repo.deleteConfirmationToken(token.getIdClient());
            return ResponseEntity.ok(new MessageResponse("Client Confirmé"));
        }
        catch (NullPointerException e){
             return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Code invalide"));
        }
    }

    @DeleteMapping("/supprimer_client/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_client(@PathVariable Long id) {
        Client c = null;
        try {
            c = C_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Client introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        Confirm_Repo.deleteConfirmationToken(id);
        C_repo.delete(c);

        Map<String, Boolean> response = new HashMap<>();
        response.put("Client supprimé avec succes", Boolean.TRUE);
        return response;
    }

}
