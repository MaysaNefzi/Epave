package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Vente {
    @Id
    @GeneratedValue
    private Long id;
  //  @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateDebut;
//    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateFin;
    @NotNull
    private int duree;
    @NotNull
    private String description;
    @NotNull
    private Boolean enchere;
    private double prixDebut;
    private String urlEngagement = null;
    @OneToMany(mappedBy="vente",cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Offre>offres=new HashSet<>();
    @NotNull
    @ManyToOne
    @JoinColumn(name="gestionnaire_id" )
    private Gestionnaire gestionnaire;

    @NotNull
    @OneToOne//(cascade = CascadeType.ALL)
    @JoinColumn(name = "sinistre_id", referencedColumnName = "id")
    private Sinistre sinistre;

    public Vente() {
    }

    public Vente(@NotNull LocalDateTime dateDebut, @NotNull LocalDateTime dateFin, int duree, @NotNull String description, @NotNull Boolean enchere, double prixDebut,
                 @NotNull Sinistre sinistre, @NotNull Set<Epaviste> epavistes, @NotNull Gestionnaire gestionnaire ,String urlEngagement) {
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.duree = duree;
        this.description = description;
        this.enchere = enchere;
        this.prixDebut = prixDebut;
        this.offres = offres;
        this.gestionnaire = gestionnaire;
        this.sinistre =sinistre;
        this.urlEngagement=urlEngagement;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDateTime getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDateTime dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDateTime getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDateTime dateFin) {
        this.dateFin = dateFin;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnchere() {
        return enchere;
    }

    public void setEnchere(Boolean enchere) {
        this.enchere = enchere;
    }

    public double getPrixDebut() {
        return prixDebut;
    }

    public void setPrixDebut(double prixDebut) {
        this.prixDebut = prixDebut;
    }
    public String getUrlEngagement() {
        return urlEngagement;
    }

    public void setUrlEngagement(String urlEngagement) {
        this.urlEngagement = urlEngagement;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }

}
