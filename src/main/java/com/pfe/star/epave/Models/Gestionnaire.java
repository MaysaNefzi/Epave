package com.pfe.star.epave.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Gestionnaire extends Utilisateur {
    @NotNull
    private String matricule;
    @NotNull
    private boolean admin;
    @OneToMany(mappedBy="gestionnaire", cascade= CascadeType.ALL)
    private Set<Vente>ventes=new HashSet<>();

    public Gestionnaire() {
    }

    public Gestionnaire(Long cin, @NotNull String username, @NotNull String password, @NotNull String nom, @NotNull String prenom, @NotNull String matricule, @NotNull Boolean admin, Set<Vente> ventes) {
        super(cin, username, password, nom, prenom);
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
