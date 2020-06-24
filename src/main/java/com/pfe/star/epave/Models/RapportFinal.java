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
    private String nomEpaviste;
    private String prenomEpaviste;
    private double montantAchat;
    private String nomClient;
    private String prenomClient;
    private  Long numPolice;
    public RapportFinal() {
    }

    public RapportFinal(String degatsConstates, @NotNull double estimationValeurEpave, @NotNull double valeurVenale, @NotNull String lieuVehicule, @NotNull boolean verif, @NotNull Sinistre sinistre,
                        String nomEpaviste, String prenomEpaviste, double montantAchat ,String nomClient, String prenomClient, Long numPolice ) {
        super(degatsConstates, estimationValeurEpave, valeurVenale, lieuVehicule, sinistre);
        this.nomEpaviste = nomEpaviste;
        this.prenomEpaviste = prenomEpaviste;
        this.montantAchat = montantAchat;
        this.numPolice=numPolice;
        this.prenomClient=prenomClient;
        this.nomClient=nomClient;
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

    public String getNomClient() {
        return nomClient;
    }

    public void setNomClient(String nomClient) {
        this.nomClient = nomClient;
    }

    public String getPrenomClient() {
        return prenomClient;
    }

    public void setPrenomClient(String prenomClient) {
        this.prenomClient = prenomClient;
    }
    public Long getNumPolice() {
        return numPolice;
    }

    public void setNumPolice(Long numPolice) {
        this.numPolice = numPolice;
    }
}
