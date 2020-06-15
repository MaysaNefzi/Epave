package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class RapportPre extends Rapport {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull Integer verif=0;//1 : verifier   -1:a corrigé

    public RapportPre() {
    }
    public RapportPre( String degatsConstates,  double estimationValeurEpave,  double valeurVenale,  String lieuVehicule,  Integer verif,  Sinistre sinistre,  String nomEpaviste,  String prenomEpaviste,  double montantAchat) {
        super(degatsConstates, estimationValeurEpave, valeurVenale, lieuVehicule, sinistre);
        this.verif=verif;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVerif() {
        return verif;
    }

    public void setVerif(Integer verif) {
        this.verif = verif;
    }
}
