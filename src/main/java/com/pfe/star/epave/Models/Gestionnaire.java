package com.pfe.star.epave.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Gestionnaire extends Utilisateur {
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private Boolean admin;
    @OneToMany(mappedBy="gestionnaire", cascade= CascadeType.ALL)
    private Set<Vente>ventes=new HashSet<>();

    public Gestionnaire() {
    }

    public Gestionnaire(@NotNull String nom, @NotNull String prenom, @NotNull Boolean admin, Set<Vente> ventes) {
        this.nom = nom;
        this.prenom = prenom;
        this.admin = admin;
        ventes = ventes;
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

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Set<Vente> getVentes() {
        return ventes;
    }

    public void setVentes(Set<Vente> ventes) {
        ventes = ventes;
    }
}
