package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.Police;
import com.pfe.star.epave.Repositories.PoliceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/police")
public class PoliceController {
    private final Logger log = LoggerFactory.getLogger(PoliceController.class);
    @Autowired
    private final PoliceRepository Police_repo;
    public PoliceController(PoliceRepository police_repo){
        Police_repo=police_repo;
    }
    @GetMapping("/police/{numPolice}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> police_ByNum(@PathVariable("numPolice") Long numPolice) {
        Optional<Police> police = Police_repo.findById(numPolice);
        return police.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
