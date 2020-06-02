
package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Offre {
    @EmbeddedId
    private OffreID id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="epaviste_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Epaviste epaviste;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="vente_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Vente vente;
    @NotNull
    @JsonFormat(pattern = "MM/dd/yyyy")
    private LocalDate dateOffre;
    @NotNull
    private double montant;
    @NotNull
    private String urlJustificatif;
    @NotNull
    private boolean offreAcceptee =false ;
    private String commentaire;

    public Offre() {
    }

    public Offre(Epaviste epaviste, Vente vente, @NotNull LocalDate dateOffre, @NotNull double montant, @NotNull String urlJustificatif, @NotNull boolean offreAcceptee, String commentaire) {
        this.epaviste = epaviste;
        this.vente = vente;
        this.dateOffre = dateOffre;
        this.montant = montant;
        this.urlJustificatif = urlJustificatif;
        this.offreAcceptee = offreAcceptee;
        this.commentaire = commentaire;
    }

    public OffreID getId() {
        return id;
    }

    public void setId(OffreID id) {
        this.id = id;
    }

    public Epaviste getEpaviste() {
        return epaviste;
    }

    public void setEpaviste(Epaviste epaviste) {
        this.epaviste = epaviste;
    }

    public Vente getVente() {
        return vente;
    }

    public void setVente(Vente vente) {
        this.vente = vente;
    }

    public LocalDate getDateOffre() {
        return dateOffre;
    }

    public void setDateOffre(LocalDate dateOffre) {
        this.dateOffre = dateOffre;
    }

    public double getMontant() {
        return montant;
    }

    public void setMontant(double montant) {
        this.montant = montant;
    }

    public String getUrlJustificatif() {
        return urlJustificatif;
    }

    public void setUrlJustificatif(String urlJustificatif) {
        this.urlJustificatif = urlJustificatif;
    }

    public boolean isOffreAcceptee() {
        return offreAcceptee;
    }

    public void setOffreAcceptee(boolean offreAcceptee) {
        this.offreAcceptee = offreAcceptee;
    }

    public String getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(String commentaire) {
        this.commentaire = commentaire;
    }

}