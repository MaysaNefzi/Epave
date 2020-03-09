package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Client;
import com.pfe.star.epave.Repositories.ClientRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collection;
import java.util.Optional;

@RestController
@RequestMapping("/client")
public class ClientController {
    private final Logger log = LoggerFactory.getLogger(ClientController.class);
    @Autowired
    private final ClientRepository C_repo;
    public ClientController(ClientRepository c_repo){
        C_repo = c_repo;
    }
    @GetMapping("/liste_client")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Client> liste_client(){
        return C_repo.findAll();
    }
    @PostMapping("/ajouter_compte_client")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Client> ajouter_compte_client(@Valid @RequestBody Client client) throws URISyntaxException {
        log.info("Créer un compte client", client);
        Client result = C_repo.save(client);
        return ResponseEntity.created(new URI("/ajouter_compte_client" + result.getCin())).body(result);
    }

    @PutMapping("/modifier_compte_client/{cin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Client> modifier_compte_client(@Valid @RequestBody Client client, @PathVariable("cin") long cin) {
        log.info("Modifier compte Client", client);
        Optional<Client> clientOptional = C_repo.findById(cin);
        if (clientOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Client c = clientOptional.get();
        c.setCin(client.getCin());
        c.setId(client.getId());
        c.setNom(client.getNom());
        c.setPrenom(client.getPrenom());
        c.setUsername(client.getUsername());
        c.setPassword(client.getPassword());
        c.setAdresse(client.getAdresse());
        c.setDelegation(client.getDelegation());
        c.setGouvernement(client.getGouvernement());
        c.setMail(client.getMail());
        c.setTel1(client.getTel1());
        c.setTel2(client.getTel2());
        Client result= C_repo.save(c);
        return ResponseEntity.ok().body(result);
    }
}
