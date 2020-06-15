package com.pfe.star.epave.Controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.star.epave.Models.*;
import com.pfe.star.epave.Repositories.OffreRepository;
import com.pfe.star.epave.Repositories.PhotoRepository;
import com.pfe.star.epave.Repositories.SinistreRepository;
import com.pfe.star.epave.Security.Payload.Response.MessageResponse;
import javassist.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.util.*;
@RestController
@RequestMapping("/offre")
public class OffreController {
    private final Logger log = LoggerFactory.getLogger(OffreController.class);
    @Autowired
    private final OffreRepository Offre_repo;
    private  final SinistreRepository S_repo;
    public OffreController(OffreRepository offre_repo , SinistreRepository s){
        Offre_repo=offre_repo;
        S_repo=s;
    }

    @GetMapping("/liste_offre")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Offre> liste_offre(){
        return Offre_repo.findAll();
    }

    @GetMapping("/offre_order/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Offre> offre_order(@PathVariable("id") Long id){
        return Offre_repo.getOffresoreder(id);
    }

    @GetMapping("/offre_best/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Offre offre_best(@PathVariable("id") Long id) throws IndexOutOfBoundsException{
        Offre o =new Offre();
        try {
            if(!Offre_repo.getOffresoreder(id).isEmpty()){
                return Offre_repo.getOffresoreder(id).get(0);
            }
            else
                return null;
        }
        catch(IndexOutOfBoundsException e){
            System.out.println(e);
        }
        return null;
    }
    @GetMapping("/offre_ById/{idV}/{idE}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Offre offre_ById(@PathVariable("idE") Long idE ,@PathVariable("idV") Long idV  ) {
        return Offre_repo.OffreById(idV, idE);
    }
    @GetMapping("/isbest/{idV}/{idE}")
    public boolean isBest(@PathVariable("idV")Long idV,@PathVariable("idE") Long idE){
        Offre offreM =offre_best(idV);
        Offre offre= offre_ById(idV, idE);
        return offreM.equals(offre);
    }
    @GetMapping("/offre_ByEpav/{idE}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Offre> offre_ByEpv(Long idE ) {
        return Offre_repo.OffreByEpv(idE);
    }

    @PostMapping("/ajouter_offre")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Offre> ajouter_offre(@Valid @RequestBody Offre offre) throws URISyntaxException {
        offre.setDateOffre(LocalDateTime.now());
        log.info("Ajouter une nouvelle Offre", offre);
        Offre result = Offre_repo.save(offre);
        return ResponseEntity.created(new URI("/ajouter_offre" + result.getId())).body(result);
    }
    @PostMapping("/upload/{numSin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public String url_Justificatif(@RequestParam("file") MultipartFile file , @PathVariable String numSin) throws URISyntaxException, JsonProcessingException {
        String pathFolder = "C:/Users/amine/Desktop/justificatif/" + numSin;
        File folder = new File(pathFolder);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Folder got created");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(pathFolder + "/" + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileDownloadUri = path.toString();
        return fileDownloadUri;
    }
//    @DeleteMapping("/supprimer_photo_BySin/{id}")
//    @CrossOrigin(origins = "http://localhost:4200")
//    public Map<String, Boolean> supprimer_p(@PathVariable Long id) {
//        List<Photo> p = P_repo.photoBySin(id);
//
//        p.stream().forEach(photo -> { P_repo.delete(photo);});
//        Map<String, Boolean> response = new HashMap<>();
//        response.put("Photos supprimées avec succes", Boolean.TRUE);
//        return response;
//    }

    @PutMapping("modifier_offre/{idE}/{idV}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Offre> modifier_offre(@Valid @RequestBody Offre offre, @PathVariable("idE") long idE, @PathVariable("idV") long idV) {
        log.info("Modifier Offre", offre);
        Offre of =new Offre();
        of = Offre_repo.OffreById(idV,idE);
        try{
        //of.setId(id);
        of.setDateOffre(LocalDateTime.now());
        of.setEpaviste(offre.getEpaviste());
        of.setVente(offre.getVente());
        of.setOffreAcceptee(offre.isOffreAcceptee());
        of.setUrlJustificatif(offre.getUrlJustificatif());
        of.setMontant(offre.getMontant());
        of.setCommentaire(offre.getCommentaire());
        of.setClientAccepte(offre.getClientAccepte());
        Offre result= Offre_repo.save(of);
        return ResponseEntity.ok().body(result);
        }
        catch(NullPointerException e){
            return ResponseEntity.notFound().build();}
    }


    @DeleteMapping("/supprimer_offre/{idE}/{idV}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_offre(@PathVariable Long idE, @PathVariable Long idV) {
        Offre o = null;
        try {
            o = Offre_repo.OffreById(idV,idE);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        Offre_repo.delete(o);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Offre supprimée avec succes", Boolean.TRUE);
        return response;
    }

}
