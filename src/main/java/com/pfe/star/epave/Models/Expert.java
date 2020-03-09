package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Expert extends Utilisateur {
    @OneToMany(mappedBy="expert", cascade= CascadeType.ALL)
    private Set<Sinistre>sinistres=new HashSet<>();

    public Expert() {
    }

    public Expert(Long cin, @NotNull String username, @NotNull String password, @NotNull String nom, @NotNull String prenom, Set<Sinistre> sinistres) {
        super(cin, username, password, nom, prenom);
        this.sinistres = sinistres;
    }

    public Set<Sinistre> getSinistres() {
        return sinistres;
    }

    public void setSinistres(Set<Sinistre> sinistres) {
        this.sinistres = sinistres;
    }
}
