package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Expert;
import com.pfe.star.epave.Repositories.ExpertRepository;
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
import java.util.List;
import java.util.Optional;
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
    public Collection<Expert> liste_exp(){
        return Exp_repo.findAll();
    }
    @GetMapping("/expert/{id}")
    public ResponseEntity<?> ex_ById(@PathVariable("id") Long id) {
        Optional<Expert> exp = Exp_repo.findById(id);
        return exp.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @PostMapping("/ajouter_exp")
    public ResponseEntity<Expert> ajouter_exp(@Valid @RequestBody Expert exp) throws URISyntaxException {
        log.info("Ajouter un nouveau Expert", exp);
        Expert result = Exp_repo.save(exp);
        return ResponseEntity.created(new URI("/ajouter_Exp" + result.getId())).body(result);
    }
    @PutMapping("modifier_exp")
    public ResponseEntity<Expert> modifier_exp(@Valid @RequestBody Expert exp, @PathVariable("id") long id) {
        log.info("Modifier Expert", exp);
        Optional<Expert> expOptional = Exp_repo.findById(id);
        if (expOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Expert e = expOptional.get();
        e.setNom(exp.getNom());
        e.setPrenom(exp.getPrenom());
        e.setLogin(exp.getLogin());
        e.setPassword(exp.getPassword());
        Expert result= Exp_repo.save(e);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("supprimer_exp")
    public ResponseEntity<?>supprimer_exp(@PathVariable("id")String id){
        log.info("Supprimer Expert",id);
        List<Expert> tt_exp= Exp_repo.findAll().stream().filter(x -> x.getId().equals(id)).collect(Collectors.toList());
        for (Expert e: tt_exp)
            Exp_repo.deleteById((e.getId()));
        return ResponseEntity.ok().build();
    }
}
