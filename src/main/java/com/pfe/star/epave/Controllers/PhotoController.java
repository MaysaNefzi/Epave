package com.pfe.star.epave.Controllers;


import com.fasterxml.jackson.databind.deser.ValueInstantiator;
import com.pfe.star.epave.Models.*;
import com.pfe.star.epave.Repositories.OffreRepository;
import com.pfe.star.epave.Repositories.PhotoRepository;
import com.pfe.star.epave.Repositories.SinistreRepository;
import com.pfe.star.epave.Repositories.VenteRepository;
import com.pfe.star.epave.Security.Payload.Response.MessageResponse;
import javassist.NotFoundException;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/photo")
public class PhotoController {
    private final Logger log = LoggerFactory.getLogger(PhotoController.class);
 @Autowired
  private  final PhotoRepository P_repo; 
  private  final SinistreRepository S_repo;
  public  PhotoController(PhotoRepository p , SinistreRepository s){
     P_repo = p;
     S_repo=s;
 }
    @GetMapping("/liste_photo")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Photo> liste_photo(){
        return P_repo.findAll()    ;
    }

    @GetMapping("/photo_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> photo_ById(@PathVariable Long id){
        Optional<Photo> Photo = P_repo.findById(id);
        return Photo.map(response-> ResponseEntity.ok().body(response))
                .orElse((new ResponseEntity<>(HttpStatus.NOT_FOUND)));
    }

    @GetMapping("/photos_BySin/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Photo> photo_BySin(@PathVariable Long id){
        return P_repo.photoBySin(id);
    }

    @PostMapping("/upload/{idS}/{nom}")
    public ResponseEntity uploadToLocalFileSystem(@RequestParam("file") MultipartFile file , @PathVariable Long idS, @PathVariable String nom) {
        Sinistre sinistre = S_repo.Sin(idS);
        String pathFolder = "C:/Users/amine/Desktop/Dossiers/Epave/src/assets/images/"+sinistre.getNumeroSinistre();
        File folder = new File(pathFolder);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Folder got created");
             } else {
                return ResponseEntity
                        .badRequest()
                        .body(new MessageResponse("Failed to create directory!"));
            }
        }
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path path = Paths.get(pathFolder+"/" + fileName);
        try {
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String fileDownloadUri = path.toString().replace("C:\\Users\\amine\\Desktop\\Dossiers\\Epave\\src\\" , "../../../"  );
        fileDownloadUri=fileDownloadUri.replace('\\','/');
        Photo p =new Photo();
        LocalDateTime d = LocalDateTime.now();
        p.setUrlPhoto(fileDownloadUri);
        p.setDateAjout(d);
        p.setAjouteePar(nom);

        p.setSinistre(sinistre);
        log.info("Ajouter photo", p);
        Photo result = P_repo.save(p);
        return ResponseEntity.ok(fileDownloadUri);
    }

    @DeleteMapping("/supprimer_photo_BySin/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_p(@PathVariable Long id) {
        List<Photo> p = P_repo.photoBySin(id);

       p.stream().forEach(photo -> { P_repo.delete(photo);});
        Map<String, Boolean> response = new HashMap<>();
        response.put("Photos supprimées avec succes", Boolean.TRUE);
        return response;
    }

    @DeleteMapping("/supprimer_photo/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_photo(@PathVariable Long id) throws IOException {
        Photo p = null;
        try {
            p = P_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Photo introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        String UrlPhoto = p.getUrlPhoto().replace("../../../"  ,"C:/Users/amine/Desktop/Dossiers/Epave/src/" );
        //Files.createFile(Paths.get(UrlPhoto));
        Path fileToDeletePath = Paths.get(UrlPhoto);
        Files.delete(fileToDeletePath);
        P_repo.delete(p);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Photo supprimée avec succes", Boolean.TRUE);
        return response;
    }
}
