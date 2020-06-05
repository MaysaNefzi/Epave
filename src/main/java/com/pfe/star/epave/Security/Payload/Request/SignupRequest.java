package com.pfe.star.epave.Security.Payload.Request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class SignupRequest {
    @Email
    @NotBlank
    private String username;
    @NotNull
    private String cin;
    @NotNull
    private String password;
    @NotNull
    private String nom;
    @NotNull
    private String prenom;
    @NotNull
    private long tel1;
    @NotNull
    private long tel2;

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

    public String getCin() {
        return cin;
    }

    public void setCin(String cin) {
        this.cin = cin;
    }

}
