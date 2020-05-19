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
public class Epaviste extends Utilisateur {
    @Column(unique = true)
    private String matriculeFiscale;
    @OneToMany(mappedBy="epaviste",cascade=CascadeType.ALL)
    @JsonIgnore
    private Set<Offre>offres= new HashSet<>();
    private Long tel;


    public Epaviste() {
    }

    public Epaviste(String cin, @Email String username, String password, String nom, String prenom, String matriculeFiscale, Set<Offre> offres, Long tel) {
        super(cin, username, password, nom, prenom);
        this.matriculeFiscale = matriculeFiscale;
        this.offres = offres;
        this.tel = tel;
    }

    public String getMatriculeFiscale() {
        return matriculeFiscale;
    }

    public void setMatriculeFiscale(String matriculeFiscale) {
        this.matriculeFiscale = matriculeFiscale;
    }


    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }
}
