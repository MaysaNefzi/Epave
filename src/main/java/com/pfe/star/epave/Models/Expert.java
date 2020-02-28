package com.pfe.star.epave.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Expert extends Utilisateur {
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @OneToMany(mappedBy="expert", cascade= CascadeType.ALL)
    private Set<Sinistre>sinistres=new HashSet<>();

    public Expert() {
    }

    public Expert(@NotNull String nom, @NotNull String prenom, Set<Sinistre> sinistres) {
        this.nom = nom;
        this.prenom = prenom;

        sinistres = sinistres;
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

    public Set<Sinistre> getSinistres() {
        return sinistres;
    }

    public void setSinistres(Set<Sinistre> sinistres) {
        sinistres = sinistres;
    }
}
