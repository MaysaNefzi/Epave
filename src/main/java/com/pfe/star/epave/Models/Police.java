package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Set;
@Entity
public class Police {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private Long numPolice;
    @NotNull
    private String typePolice;
    @NotNull
    private String branchePolice;
    @NotNull
    private LocalDate dateEffet;
    @NotNull
    private LocalDate dateEcheance;
    @NotNull
    private String etatPolice;
    @NotNull
    @ManyToOne
    @JoinColumn(name="client_id" )
    private Client client;
    @OneToMany(mappedBy="police", cascade= CascadeType.ALL)
    @JsonIgnore
    private Set<Sinistre>sinistres;

    public Police() {
    }

    public Police(Long numPolice, String typePolice, String branchePolice, LocalDate dateEffet, LocalDate dateEcheance, String etatPolice, Client client, Set<Sinistre> sinistres) {
        this.numPolice = numPolice;
        this.typePolice = typePolice;
        this.branchePolice = branchePolice;
        this.dateEffet = dateEffet;
        this.dateEcheance = dateEcheance;
        this.etatPolice = etatPolice;
        this.client = client;
        this.sinistres = sinistres;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getNumPolice() {
        return numPolice;
    }

    public void setNumPolice(Long numPolice) {
        this.numPolice = numPolice;
    }

    public String getTypePolice() {
        return typePolice;
    }

    public void setTypePolice(String typePolice) {
        this.typePolice = typePolice;
    }

    public String getBranchePolice() {
        return branchePolice;
    }

    public void setBranchePolice(String branchePolice) {
        this.branchePolice = branchePolice;
    }

    public LocalDate getDateEffet() {
        return dateEffet;
    }

    public void setDateEffet(LocalDate dateEffet) {
        this.dateEffet = dateEffet;
    }

    public LocalDate getDateEcheance() {
        return dateEcheance;
    }

    public void setDateEcheance(LocalDate dateEcheance) {
        this.dateEcheance = dateEcheance;
    }

    public String getEtatPolice() {
        return etatPolice;
    }

    public void setEtatPolice(String etatPolice) {
        this.etatPolice = etatPolice;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Set<Sinistre> getSinistres() {
        return sinistres;
    }

    public void setSinistres(Set<Sinistre> sinistres) {
        this.sinistres = sinistres;
    }
}
