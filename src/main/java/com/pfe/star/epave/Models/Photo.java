package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Photo {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String urlPhoto;
    @NotNull
    private String ajouteePar;
    @NotNull
    private LocalDate dateAjout;
    @NotNull
    @ManyToOne
    @JoinColumn(name="sinistre_id" )
    private Sinistre sinistre;

    public Photo() {
    }

    public Photo(String urlPhoto, String ajouteePar, LocalDate dateAjout, Sinistre sinistre) {
        this.urlPhoto = urlPhoto;
        this.ajouteePar = ajouteePar;
        this.dateAjout = dateAjout;
        this.sinistre = sinistre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getAjouteePar() {
        return ajouteePar;
    }

    public void setAjouteePar(String ajouteePar) {
        this.ajouteePar = ajouteePar;
    }

    public LocalDate getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDate dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }
}
