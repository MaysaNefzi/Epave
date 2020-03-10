package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Expert;
import com.pfe.star.epave.Repositories.ExpertRepository;
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
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/expert")
public class ExpertController {
    private final Logger log = LoggerFactory.getLogger(ExpertController.class);

    @Autowired
    private final ExpertRepository Exp_repo;
    public ExpertController(ExpertRepository exp_repo){
        Exp_repo=exp_repo;
    }

    @GetMapping("/liste_exp")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Expert> liste_exp(){
        return Exp_repo.findAll();
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
    public ResponseEntity<Expert> ajouter_exp(@Valid @RequestBody Expert exp) throws URISyntaxException {
        log.info("Ajouter un nouveau Expert", exp);
        Expert result = Exp_repo.save(exp);
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
        e.setId(exp.getId());
        e.setNom(exp.getNom());
        e.setPrenom(exp.getPrenom());
        e.setUsername(exp.getUsername());
        e.setPassword(exp.getPassword());
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
