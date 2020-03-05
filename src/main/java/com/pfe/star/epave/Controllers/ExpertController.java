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
    public Collection<Expert> liste_exp(){
        return Exp_repo.findAll();
    }

    @GetMapping("/expert/{cin}")
    public ResponseEntity<?> ex_ById(@PathVariable("cin") Long cin) {
        Optional<Expert> exp = Exp_repo.findById(cin);
        return exp.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/ajouter_exp")
    public ResponseEntity<Expert> ajouter_exp(@Valid @RequestBody Expert exp) throws URISyntaxException {
        log.info("Ajouter un nouveau Expert", exp);
        Expert result = Exp_repo.save(exp);
        return ResponseEntity.created(new URI("/ajouter_Exp" + result.getCin())).body(result);
    }

    @PutMapping("/modifier_exp/{cin}")
    public ResponseEntity<Expert> modifier_exp(@Valid @RequestBody Expert exp, @PathVariable("cin") long cin) {
        log.info("Modifier Expert", exp);
        Optional<Expert> expOptional = Exp_repo.findById(cin);
        if (expOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Expert e = expOptional.get();
        e.setCin(exp.getCin());
        e.setId(exp.getId());
        e.setNom(exp.getNom());
        e.setPrenom(exp.getPrenom());
        e.setUsername(exp.getUsername());
        e.setPassword(exp.getPassword());
        Expert result= Exp_repo.save(e);
        return ResponseEntity.ok().body(result);
    }

    @DeleteMapping("/supprimer_exp/{cin}")
    public Map<String, Boolean> supprimer_exp(@PathVariable Long cin) {
        Expert e = null;
        try {
            e = Exp_repo.findById(cin)
                    .orElseThrow(() -> new NotFoundException("Expert introuvable pour cette cin: " + cin));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        Exp_repo.delete(e);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Expert supprimé avec succes", Boolean.TRUE);
        return response;
    }


}
