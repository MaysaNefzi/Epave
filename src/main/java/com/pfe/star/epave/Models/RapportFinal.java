package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class RapportFinal extends Rapport {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String nomEpaviste;
    @NotNull
    private String prenomEpaviste;
    @NotNull
    private double montantAchat;

    public RapportFinal() {
    }

    public RapportFinal(@NotNull String degatsConstates, @NotNull double estimationValeurEpave, @NotNull double valeurVenale, @NotNull String lieuVehicule, @NotNull boolean verif, @NotNull Sinistre sinistre, @NotNull String nomEpaviste, @NotNull String prenomEpaviste, @NotNull double montantAchat) {
        super(degatsConstates, estimationValeurEpave, valeurVenale, lieuVehicule, sinistre);
        this.nomEpaviste = nomEpaviste;
        this.prenomEpaviste = prenomEpaviste;
        this.montantAchat = montantAchat;
    }
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomEpaviste() {
        return nomEpaviste;
    }

    public void setNomEpaviste(String nomEpaviste) {
        this.nomEpaviste = nomEpaviste;
    }

    public String getPrenomEpaviste() {
        return prenomEpaviste;
    }

    public void setPrenomEpaviste(String prenomEpaviste) {
        this.prenomEpaviste = prenomEpaviste;
    }

    public double getMontantAchat() {
        return montantAchat;
    }

    public void setMontantAchat(double montantAchat) {
        this.montantAchat = montantAchat;
    }
}
