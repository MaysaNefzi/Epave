package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Rapport {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String degatsConstates;
    @NotNull
    private double estimationValeurEpave;
    @NotNull
    private double valeurVenale;
    @NotNull
    private String lieuVehicule;
    @NotNull boolean verif=true;
    @NotNull
    @ManyToOne
    @JoinColumn(name="sinistre_id" )
    private Sinistre sinistre;

    public Rapport() {
    }

    public Rapport(@NotNull String degatsConstates, @NotNull double estimationValeurEpave, @NotNull double valeurVenale, @NotNull String lieuVehicule, @NotNull boolean verif, @NotNull Sinistre sinistre) {
        this.degatsConstates = degatsConstates;
        this.estimationValeurEpave = estimationValeurEpave;
        this.valeurVenale = valeurVenale;
        this.lieuVehicule = lieuVehicule;
        this.verif = verif;
        this.sinistre = sinistre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDegatsConstates() {
        return degatsConstates;
    }

    public void setDegatsConstates(String degatsConstates) {
        this.degatsConstates = degatsConstates;
    }

    public double getEstimationValeurEpave() {
        return estimationValeurEpave;
    }

    public void setEstimationValeurEpave(double estimationValeurEpave) {
        this.estimationValeurEpave = estimationValeurEpave;
    }

    public double getValeurVenale() {
        return valeurVenale;
    }

    public void setValeurVenale(double valeurVenale) {
        this.valeurVenale = valeurVenale;
    }

    public String getLieuVehicule() {
        return lieuVehicule;
    }

    public void setLieuVehicule(String lieuVehicule) {
        this.lieuVehicule = lieuVehicule;
    }

    public Sinistre getSinistre() {
        return sinistre;
    }

    public void setSinistre(Sinistre sinistre) {
        this.sinistre = sinistre;
    }

    public boolean isVerif() {
        return verif;
    }

    public void setVerif(boolean verif) {
        this.verif = verif;
    }
}
