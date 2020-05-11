package com.pfe.star.epave.Models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Client extends Utilisateur {

    private String adresse;
    private String gouvernement;
    private String delegation;
    private long tel1;
    private long tel2;
    @NotNull
    private boolean compteActif=false;
    @OneToMany(mappedBy="client", cascade= CascadeType.ALL)
    @JsonIgnore
    private Set<Police>polices = new HashSet<>();

    public Client() {
    }

    public Client(String cin,@Email  String username,  String password,  String nom,  String prenom,  String adresse,  String gouvernement,  String delegation, long tel1,  long tel2,  boolean compteActif,   Set<Police> polices) {
        super(cin, username, password, nom, prenom);
        this.adresse = adresse;
        this.gouvernement = gouvernement;
        this.delegation = delegation;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.compteActif = compteActif;
        this.polices = polices;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getGouvernement() {
        return gouvernement;
    }

    public void setGouvernement(String gouvernement) {
        this.gouvernement = gouvernement;
    }

    public String getDelegation() {
        return delegation;
    }

    public void setDelegation(String delegation) {
        this.delegation = delegation;
    }

    public long getTel1() {
        return tel1;
    }

    public void setTel1(long tel1) {
        this.tel1 = tel1;
    }

    public long getTel2() {
        return tel2;
    }

    public void setTel2(long tel2) {
        this.tel2 = tel2;
    }
    public boolean isCompteActif() {
        return compteActif;
    }

    public void setCompteActif(boolean compteActif) {
        this.compteActif = compteActif;
    }

    public Set<Police> getPolices() {
        return polices;
    }

    public void setPolices(Set<Police> polices) {
        this.polices = polices;
    }
}
