package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Repositories.PoliceRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class PoliceController {
    private final Logger log = LoggerFactory.getLogger(PoliceController.class);
    @Autowired
    private final PoliceRepository Police_repo;
    public PoliceController(PoliceRepository police_repo){
        Police_repo=police_repo;
    }
}
