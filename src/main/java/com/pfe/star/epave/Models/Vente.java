package com.pfe.star.epave.Models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;
@Entity
public class Vente extends Sinistre{
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private LocalDate dateCreation;
    @NotNull
    private LocalDate dateDebut;
    @NotNull
    private LocalDate dateFin;
    private int duree;
    @NotNull
    private String description;
    private Boolean enchere;
    private double prixDebut;
    @OneToMany(mappedBy="vente",cascade=CascadeType.ALL)
    private Set<Offre>offres=new HashSet<>();
    @NotNull
    @ManyToOne
    @JoinColumn(name="gestionnaire_id" )
    private Gestionnaire gestionnaire;

    public Vente() {
    }

    public Vente(@NotNull LocalDate dateCreation, @NotNull LocalDate dateDebut, @NotNull LocalDate dateFin, int duree, @NotNull String description, @NotNull Boolean enchere, double prixDebut, @NotNull Sinistre sinistre, @NotNull Set<Epaviste> epavistes, @NotNull Gestionnaire gestionnaire) {
        this.dateCreation = dateCreation;
        this.dateDebut = dateDebut;
        this.dateFin = dateFin;
        this.duree = duree;
        this.description = description;
        this.enchere = enchere;
        this.prixDebut = prixDebut;
        this.offres = offres;
        this.gestionnaire = gestionnaire;
    }

    @Override
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(LocalDate dateCreation) {
        this.dateCreation = dateCreation;
    }

    public LocalDate getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(LocalDate dateDebut) {
        this.dateDebut = dateDebut;
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public int getDuree() {
        return duree;
    }

    public void setDuree(int duree) {
        this.duree = duree;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getEnchere() {
        return enchere;
    }

    public void setEnchere(Boolean enchere) {
        this.enchere = enchere;
    }

    public double getPrixDebut() {
        return prixDebut;
    }

    public void setPrixDebut(double prixDebut) {
        this.prixDebut = prixDebut;
    }


    public Set<Offre> getOffres() {
        return offres;
    }

    public void setOffres(Set<Offre> offres) {
        this.offres = offres;
    }

    public Gestionnaire getGestionnaire() {
        return gestionnaire;
    }

    public void setGestionnaire(Gestionnaire gestionnaire) {
        this.gestionnaire = gestionnaire;
    }
}
