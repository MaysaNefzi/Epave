package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Epaviste extends Utilisateur {
    @NotNull
    private String matriculeFiscale;
    @NotNull
    private boolean compteActif=false;
    @OneToMany(mappedBy="epaviste",cascade=CascadeType.ALL)
    private Set<Offre>offres= new HashSet<>();


    public Epaviste() {
    }

    public Epaviste(String cin, @NotNull String username, @NotNull String password, @NotNull String nom, @NotNull String prenom, @NotNull String role, @NotNull String matriculeFiscale, @NotNull boolean compteActif, Set<Offre> offres) {
        super(cin, username, password, nom, prenom, role);
        this.matriculeFiscale = matriculeFiscale;
        this.compteActif = compteActif;
        this.offres = offres;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }

    public boolean isCompteActif() {
        return compteActif;
    }

    public void setCompteActif(boolean compteActif) {
        this.compteActif = compteActif;
    }

    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }
}
