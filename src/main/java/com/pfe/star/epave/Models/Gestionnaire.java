package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Gestionnaire extends Utilisateur {
    @Column(unique = true)
    private String matricule;

    @OneToMany(mappedBy="gestionnaire", cascade= CascadeType.ALL)
    @JsonIgnore
    private Set<Vente>ventes=new HashSet<>();


    public Gestionnaire() {
    }

    public Gestionnaire(String cin, @Email String username,  String password,  String nom,  String prenom, String matricule,  Set<Vente> ventes) {
        super(cin, username, password, nom, prenom);
        this.matricule = matricule;
        this.ventes = ventes;
    }

    public String getMatricule() {
        return matricule;
    }

    public void setMatricule(String matricule) {
        this.matricule = matricule;
    }

    public Set<Vente> getVentes() {
        return ventes;
    }

    public void setVentes(Set<Vente> ventes) {
        this.ventes = ventes;
    }
}
