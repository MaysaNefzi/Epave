package com.pfe.star.epave.Models;


import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Client extends Utilisateur {
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
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


    public Client(String nom, String prenom, String adresse, String gouvernement, String delegation, long tel1, long tel2, String mail, Set<Police> polices) {
        this.nom = nom;
        this.prenom = prenom;
        this.adresse = adresse;
        this.gouvernement = gouvernement;
        this.delegation = delegation;
        this.tel1 = tel1;
        this.tel2 = tel2;
        this.mail = mail;
        this.polices = polices;
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

    public Long getTel1() {
        return tel1;
    }

    public void setTel1(Long tel1) {
        this.tel1 = tel1;
    }

    public Long getTel2() {
        return tel2;
    }

    public void setTel2(Long tel2) {
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
