package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Sinistre {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    @Column(unique = true)
    private String numeroSinistre;
    @NotNull
    @Column(unique =true )
    private String immatriculation;
    @NotNull
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateAccident;
    @NotNull
    @Column(unique = true)
    private String numChassis;
    @NotNull
    private String marque;
    @NotNull
    private Boolean epave;
    @NotNull
    private String modele;
    @NotNull
    private String etatSinistre;
    @NotNull
    @ManyToOne
    @JoinColumn(name="police_id" )
    private Police police;
    @NotNull
    @ManyToOne
    @JoinColumn(name="expert_id" )
    private Expert expert;
    @OneToMany(mappedBy="sinistre", cascade= CascadeType.ALL)
    @JsonIgnore
    private Set<Rapport> rapports =new HashSet<>();

    @OneToMany(mappedBy="sinistre", cascade= CascadeType.ALL)
    private Set<Photo> photos= new HashSet<>();

    public Sinistre(){}

    public Sinistre(@NotNull String numeroSinistre, @NotNull String immatriculation, @NotNull LocalDate dateAccident, @NotNull String numChassis, @NotNull String marque, @NotNull Boolean epave, @NotNull String modele, @NotNull String etatSinistre, @NotNull Police police, @NotNull Expert expert, Set<Rapport> rapports, Set<Photo> photos) {
        this.numeroSinistre = numeroSinistre;
        this.immatriculation = immatriculation;
        this.dateAccident = dateAccident;
        this.numChassis = numChassis;
        this.marque = marque;
        this.epave = epave;
        this.modele = modele;
        this.etatSinistre = etatSinistre;
        this.police = police;
        this.expert = expert;
        this.rapports = rapports;
        this.photos = photos;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumeroSinistre() {
        return numeroSinistre;
    }

    public void setNumeroSinistre(String numeroSinistre) {
        this.numeroSinistre = numeroSinistre;
    }

    public String getImmatriculation() {
        return immatriculation;
    }

    public void setImmatriculation(String immatriculation) {
        this.immatriculation = immatriculation;
    }

    public LocalDate getDateAccident() {
        return dateAccident;
    }

    public void setDateAccident(LocalDate dateAccident) {
        this.dateAccident = dateAccident;
    }

    public String getNumChassis() {
        return numChassis;
    }

    public void setNumChassis(String numChassis) {
        this.numChassis = numChassis;
    }

    public String getMarque() {
        return marque;
    }

    public void setMarque(String marque) {
        this.marque = marque;
    }

    public Boolean getEpave() {
        return epave;
    }

    public void setEpave(Boolean epave) {
        this.epave = epave;
    }

    public String getModele() {
        return modele;
    }

    public void setModele(String modele) {
        this.modele = modele;
    }

    public Police getPolice() {
        return police;
    }

    public void setPolice(Police police) {
        this.police = police;
    }

    public Expert getExpert() {
        return expert;
    }

    public void setExpert(Expert expert) {
        this.expert = expert;
    }

    public Set<Rapport> getRapports() {
        return rapports;
    }

    public void setRapports(Set<Rapport> rapports) {
        this.rapports = rapports;
    }

    public Set<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Set<Photo> photos) {
        this.photos = photos;
    }

    public String getEtatSinistre() {
        return etatSinistre;
    }

    public void setEtatSinistre(String etatSinistre) {
        this.etatSinistre = etatSinistre;
    }
}
