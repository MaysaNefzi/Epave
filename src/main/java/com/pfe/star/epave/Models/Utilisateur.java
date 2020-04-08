package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Utilisateur {
    @Id
    @GeneratedValue
    private Long id;
    private String cin;
    @NotNull
    private String username;
    @NotNull
    private String password;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private String role;
   /* @NotNull
    private String email;*/

    public Utilisateur() {
    }

    public Utilisateur(String cin, @NotNull String username, @NotNull String password, @NotNull String nom, @NotNull String prenom, @NotNull String role /*,@NotNull String email*/) {
        this.cin = cin;
        this.username = username;
        this.password = password;
        this.nom = nom;
        this.prenom = prenom;
        this.role = role;
        //this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

  /*  public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }*/

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
