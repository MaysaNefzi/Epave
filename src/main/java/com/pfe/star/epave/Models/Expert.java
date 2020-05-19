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
public class Expert extends Utilisateur {
    @OneToMany(mappedBy="expert", cascade= CascadeType.ALL)
    @JsonIgnore
    private Set<Sinistre>sinistres;
    private Long tel;

    public Expert() {
    }

    public Expert(String cin, @Email String username, String password, String nom, String prenom, Set<Sinistre> sinistres, Long tel) {
        super(cin, username, password, nom, prenom);
        this.sinistres = sinistres;
        this.tel = tel;
    }

    public Long getTel() {
        return tel;
    }

    public void setTel(Long tel) {
        this.tel = tel;
    }

    public Set<Sinistre> getSinistres() {
        return sinistres;
    }

    public void setSinistres(Set<Sinistre> sinistres) {
        this.sinistres = sinistres;
    }
}
