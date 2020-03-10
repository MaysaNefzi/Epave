package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Client;
import com.pfe.star.epave.Models.Police;
import com.pfe.star.epave.Repositories.PoliceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/police")
public class PoliceController {
    private final Logger log = LoggerFactory.getLogger(PoliceController.class);
    @Autowired
    private final PoliceRepository Police_repo;
    public PoliceController(PoliceRepository police_repo){
        Police_repo=police_repo;
    }
    @GetMapping("/police_ByID/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> police_ByID(@PathVariable("id") Long id) {
        Optional<Police> police = Police_repo.findById(id);
        return police.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/client_ByNumPolice/{nom}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Police> client_ByNumPolice(@PathVariable String numeroPolice) {
        return Police_repo.findAll().stream().filter(x -> x.getNumPolice().equals(numeroPolice)).collect(Collectors.toList());

    }
}
