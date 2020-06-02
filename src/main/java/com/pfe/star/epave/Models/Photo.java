package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    private LocalDateTime dateAjout;
    @NotNull
    @ManyToOne
    @JsonIgnore
    @JoinColumn(name="sinistre_id" )
    private Sinistre sinistre;

    public Photo() {
    }

    public Photo(String urlPhoto, String ajouteePar, LocalDateTime dateAjout, Sinistre sinistre) {
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

    public LocalDateTime getDateAjout() {
        return dateAjout;
    }

    public void setDateAjout(LocalDateTime dateAjout) {
        this.dateAjout = dateAjout;
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }
}
