package com.pfe.star.epave.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Epaviste extends Utilisateur {
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private boolean compteActif=false;
    @OneToMany(mappedBy="epaviste",cascade=CascadeType.ALL)
    private Set<Offre>offres= new HashSet<>();


    public Epaviste() {
    }

    public Epaviste(@NotNull String nom, @NotNull String prenom, boolean compteActif, Set<Vente> epaves) {
        this.nom = nom;
        this.prenom = prenom;
        this.compteActif = compteActif;
        this.offres = offres;
    }


    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
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
