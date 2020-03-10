package com.pfe.star.epave.Models;


import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Client extends Utilisateur {
    @NotNull
    private String adresse;
    @NotNull
    private String gouvernement;
    @NotNull
    private String delegation;
    @NotNull
    private long tel1;
    @NotNull
    private long tel2;
    private String mail;
    @NotNull
    @OneToMany(mappedBy="client", cascade= CascadeType.ALL)
    private Set<Police>polices = new HashSet<>();

    public Client() {
    }

    public Client(String cin, @NotNull String username, @NotNull String password, @NotNull String nom, @NotNull String prenom, @NotNull String adresse, @NotNull String gouvernement, @NotNull String delegation, @NotNull long tel1, @NotNull long tel2, String mail, @NotNull Set<Police> polices) {
        super(cin, username, password, nom, prenom);
        this.adresse = adresse;
        this.gouvernement = gouvernement;
        this.delegation = delegation;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.mail = mail;
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

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public Set<Police> getPolices() {
        return polices;
    }

    public void setPolices(Set<Police> polices) {
        this.polices = polices;
    }
}
