package com.pfe.star.epave.Controllers;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.Vente;
import com.pfe.star.epave.Repositories.OffreRepository;
import com.pfe.star.epave.Repositories.VenteRepository;
import com.pfe.star.epave.Security.Payload.Request.EngagementRequest;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/vente")
public class VenteController {
    private final Logger log = LoggerFactory.getLogger(VenteController.class);
    @Autowired
    private final VenteRepository V_repo;
    private final OffreRepository O_repo;

    private boolean enchere= true;
    public VenteController(VenteRepository v_repo , OffreRepository o_repo){
        V_repo=v_repo;
        O_repo=o_repo;
    }
    @GetMapping("/liste_vente")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> liste_vente(){
        return V_repo.findAll();
    }


    @GetMapping("/vente_ById/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> vente_ById(@PathVariable("id") Long id) {
        Optional<Vente> vente = V_repo.findById(id);
        return vente.map(response -> ResponseEntity.ok().body(response))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    // all vente participée
    @GetMapping("/vente_ByEpv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_By_Epv(@PathVariable("id") Long id){
        return V_repo.Vente_By_Epv(id);
    }

    //all vente participee ecnhere
    @GetMapping("/vente_enchre_ByEpv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_enchre_By_Epv(@PathVariable("id") Long id){
        return V_repo.Vente_By_Epv(id).stream().filter(this::est_Enchere).collect(Collectors.toList());
    }
    //all vente participee ecnhere
    @GetMapping("/vente_enchre_encours_ByEpv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_enchre__encours_By_Epv(@PathVariable("id") Long id){
        LocalDateTime d = LocalDateTime.now();
        List<Vente> L =  V_repo.Vente_By_Epv(id).stream().filter(this::est_Enchere).collect(Collectors.toList());
        return  L.stream().filter(x -> x.getDateFin().isAfter(d)).collect(Collectors.toList());

    }

    // vente en cours participée
    @GetMapping("/vente_encours_By_Epv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_encours_By_Epv(@PathVariable("id") Long id){
        LocalDateTime d = LocalDateTime.now();
        return  V_repo.Vente_By_Epv(id).stream().filter(x -> x.getDateFin().isAfter(d)).collect(Collectors.toList());
    }

    @GetMapping("/vente_BySinId/{sin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> rapport_BySinId(@PathVariable Long sin) {
        return V_repo.findAll().stream().filter(x -> x.getSinistre().getId().equals(sin)).collect(Collectors.toList());

    }

    // vente encours non participée
    @GetMapping("/vente_encours_N_ByEpv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_encours_N_By_Epv(@PathVariable("id") Long id){
        List<Vente> l2 = Vente_By_Epv(id);
        Collection<Vente> l1 = liste_vente();
        List<Vente> diff = l1.stream()
                .filter(i -> !l2.contains(i))
                .collect (Collectors.toList());
        LocalDateTime d = LocalDateTime.now();
        return diff.stream().filter(x -> x.getDateFin().isAfter(d)).collect(Collectors.toList());
    }
    @GetMapping("/vente_terminee_By_Epv_Sin/{idE}/{sin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Vente Vente_terminee_By_Epv_Sin(@PathVariable("idE") Long idE , @PathVariable("sin") Long Sinistre) throws NullPointerException{

        Vente v =null;
        try {
            List<Vente> epv = Vente_By_Epv(idE);
            Vente sin = V_repo.Vente_By_Sin(Sinistre);
            Long idvente=sin.getId();
           for(int i =0 ; i<epv.size();i++){
               if(epv.get(i).getId()==idvente)
                   return sin;
           }
       }
       catch (NullPointerException e){
           e.printStackTrace();
       }
        return v;
    }
    // all vente termine et participee
    @GetMapping("/vente_terminee_By_Epv/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_terminee_By_Epv(@PathVariable("id") Long id){
        LocalDateTime d = LocalDateTime.now();
        List<Vente> l1 = V_repo.Vente_By_Epv(id);
        List<Vente> l2 = new ArrayList<>() ;
        for(int i=0;i<l1.size();i++){
            int x = l1.get(i).getDateFin().compareTo(d);
            log.info("compare   :   " + x);
            if(x<=0)
                l2.add(l1.get(i));

        }
        return  l2;
    }

    @GetMapping("/vente_Encours")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_Encours(){
        LocalDateTime d = LocalDateTime.now();
        return V_repo.findAllByDateFin().stream().filter(x -> x.getDateFin().isAfter(d)).collect(Collectors.toList());

    }

    @GetMapping("/vente_EncoursRech/{sin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_EncoursRech(@PathVariable("sin") Long sin){
        LocalDateTime d = LocalDateTime.now();
        return V_repo.findAllByDateFin().stream().filter(x -> x.getDateFin().isAfter(d) && x.getSinistre().getId().equals(sin)).collect(Collectors.toList());

    }
    /*public Collection<Vente> vente_ByEpv(@PathVariable("idE") Long idE ) {
        Collection<Offre> o = O_repo.OffreByEpv(idE);
    }*/

    @PostMapping("/ajouter_vente")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Vente> ajouter_vente(@Valid @RequestBody Vente vente) throws URISyntaxException {
        vente.setDateDebut(LocalDateTime.now());
        LocalDateTime debut = vente.getDateDebut();
        vente.setDateFin(debut.plusDays(vente.getDuree()));
        log.info("Ajouter une nouvelle vente", vente);
        Vente result = V_repo.save(vente);
        String pathFolder = "C:/Users/amine/Desktop/ventes/"+result.getSinistre().getImmatriculation();
        File folder = new File(pathFolder);
        if (!folder.exists()) {
            if (folder.mkdir()) {
                System.out.println("Folder got created");
            } else {
                System.out.println("Failed to create directory!");
            }
        }
        return ResponseEntity.created(new URI("/ajouter_vente" + result.getId())).body(result);
    }

    private boolean est_Enchere(Vente sin) {
        return sin.getEnchere().equals(enchere);
    }
    private boolean est_AppelOffre(Vente sin){return sin.getEnchere().equals(!enchere);
    }

    @GetMapping("/liste_enchere")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> liste_enchere() {
        return V_repo.findAll().stream().filter(this::est_Enchere).collect(Collectors.toList());
    }
    @GetMapping("/liste_appelOffre")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> liste_appelOffre() {
        return V_repo.findAll().stream().filter(this::est_AppelOffre).collect(Collectors.toList());
    }

    @GetMapping("/Etat/{id}")
    public boolean Etat(@PathVariable("id") Long id){
        LocalDateTime now = LocalDateTime.now();
        Optional<Vente> v = V_repo.findById(id);
        int x =v.get().getDateFin().compareTo(now);
        if(x>0){
            System.out.println("ss   : "+x);
            return false;
        }
        else {
            System.out.println("mm   : "+x);
            return true;
        }
    }

    public boolean traitement_encours(Vente v){
        LocalDateTime now = LocalDateTime.now();
        int x =v.getDateFin().compareTo(now);
        if(x>0){
            System.out.println("ss   : "+x);
            return false;
        }
        else {
            System.out.println("mm   : "+x);
            return true;
        }
    }

    @GetMapping("/traitement")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> traitement() {
        return V_repo.findAll().stream().filter(this::traitement_encours).collect(Collectors.toList());
    }

    @GetMapping("/traitementSin/{sin}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Collection<Vente> traitementSin(@PathVariable("sin") Long sin) {
        return V_repo.findAll().stream().filter(x -> traitement_encours(x) && x.getSinistre().getId().equals(sin)).collect(Collectors.toList());
    }

    @GetMapping("/offre_acceptee/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Offre OffreAcceptee(@PathVariable Long id) {
        return O_repo.OffreAcceptee(id);

    }


    @PutMapping("/modifier_vente/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Vente> modifier_vente(@Valid @RequestBody Vente vente, @PathVariable("id") long id) {
        log.info("Modifier vente", vente);
        Optional<Vente> venteOptional = V_repo.findById(id);
        if (venteOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Vente v = venteOptional.get();
        v.setId(id);
        v.setDateDebut(LocalDateTime.now());
        System.out.println(vente.getDuree());
        v.setDateFin(vente.getDateDebut().plusDays(vente.getDuree()));
        v.setDescription(vente.getDescription());
        v.setDuree(vente.getDuree());
        v.setEnchere(vente.getEnchere());
        v.setPrixDebut(vente.getPrixDebut());
        Vente result= V_repo.save(v);
        return ResponseEntity.ok().body(result);
    }
    @PutMapping("/lettre_engagement/{idV}/{idE}/{clientAccept}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<?> modifier_vente(@RequestParam("file") MultipartFile file, @PathVariable("idV") long idV, @PathVariable("idE") long idE , @PathVariable("clientAccept") Integer clientAccept)throws URISyntaxException, JsonProcessingException {
        Offre o = null;
        try {
            o = O_repo.OffreById(idV,idE);
        } catch (NullPointerException ex) {
            ex.printStackTrace();
        }
        Vente v = o.getVente();
        o.setClientAccepte(clientAccept);

        String pathFolder = "C:/Users/amine/Desktop/Lettre_engagement/" +v.getSinistre().getNumeroSinistre();
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
        v.setUrlEngagement(fileDownloadUri);
        Offre result= O_repo.save(o);
        V_repo.save(v);
        return ResponseEntity.ok().body(result);
    }


    @PutMapping("/arreter_vente/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public ResponseEntity<Vente> arretet_vente( @PathVariable("id") long id) {
        Optional<Vente> venteOptional = V_repo.findById(id);
        if (venteOptional.isEmpty())
            return ResponseEntity.notFound().build();
        Vente v = venteOptional.get();
        v.setId(id);
        v.setDateFin(LocalDateTime.now());
        v.setDuree(v.getDateFin().compareTo(v.getDateDebut()));
        Vente result= V_repo.save(v);
        return ResponseEntity.ok().body(result);
    }
    @DeleteMapping("/supprimer_vente/{id}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Map<String, Boolean> supprimer_vente(@PathVariable Long id) {
        Vente v = null;
        try {
            v = V_repo.findById(id)
                    .orElseThrow(() -> new NotFoundException("Vente introuvable pour cet id: " + id));
        } catch (NotFoundException ex) {
            ex.printStackTrace();
        }
        V_repo.delete(v);
        Map<String, Boolean> response = new HashMap<>();
        response.put("Vente supprimée avec succes", Boolean.TRUE);
        return response;
    }
    @GetMapping("/traitement_vente/{idV}")
    @CrossOrigin(origins = "http://localhost:4200")
    public Vente traitement_vente( @PathVariable Long idV){
        Vente v =null;
        if(!V_repo.findById(idV).isEmpty()){
            v= V_repo.findById(idV).get();
            LocalDateTime now = LocalDateTime.now();
            int x =v.getDateFin().compareTo(now);
            if(x>0){
                System.out.println("ss   : "+x);
                return null;
            }
            else {
                System.out.println("mm   : "+x);
                return v;
            }
        }
        return v;
    }

    @GetMapping("/vente_encours_By_EpvSin/{idE}/{idS}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_encours_By_EpvSin(@PathVariable("idE") Long id,@PathVariable("idS") Long idS){
        LocalDateTime d = LocalDateTime.now();
        return  V_repo.Vente_By_Epv(id).stream().filter(x -> x.getDateFin().isAfter(d) && x.getSinistre().getId().equals(idS)).collect(Collectors.toList());
    }
    @GetMapping("/vente_encours_N_ByEpvSin/{id}/{idS}")
    @CrossOrigin(origins = "http://localhost:4200")
    public List<Vente> Vente_encours_N_By_EpvSin(@PathVariable("id") Long id, @PathVariable("idS") Long idS){
        List<Vente> l2 = Vente_By_Epv(id);
        Collection<Vente> l1 = liste_vente();
        List<Vente> diff = l1.stream()
                .filter(i -> !l2.contains(i))
                .collect (Collectors.toList());
        LocalDateTime d = LocalDateTime.now();
        return diff.stream().filter(x -> x.getDateFin().isAfter(d) && x.getSinistre().getId().equals(idS)).collect(Collectors.toList());
    }
}
