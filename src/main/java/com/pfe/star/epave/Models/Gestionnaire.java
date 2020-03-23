package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Gestionnaire extends Utilisateur {
    @NotNull
    private String matricule;
    @NotNull
    private boolean admin;
    @OneToMany(mappedBy="gestionnaire", cascade= CascadeType.ALL)
    private Set<Vente>ventes=new HashSet<>();

    public Gestionnaire() {
    }

    public Gestionnaire(String cin, @NotNull String username, @NotNull String password, @NotNull String nom, @NotNull String prenom, @NotNull String email, @NotNull String matricule, @NotNull boolean admin, Set<Vente> ventes) {
        super(cin, username, password, nom, prenom, email);
        this.matricule = matricule;
        this.admin = admin;
        this.ventes = ventes;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public Set<Vente> getVentes() {
        return ventes;
    }

    public void setVentes(Set<Vente> ventes) {
        this.ventes = ventes;
    }
}
