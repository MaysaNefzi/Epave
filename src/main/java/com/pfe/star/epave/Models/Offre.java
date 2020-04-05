package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
@JsonIdentityInfo(generator= ObjectIdGenerators.IntSequenceGenerator.class)
public class Offre {
    @EmbeddedId
    private OffreID id;
    @ManyToOne(optional=false)
    @JoinColumn(name="epaviste_id",referencedColumnName = "id",insertable = false,updatable = false)

    private Epaviste epaviste;
    @ManyToOne(optional=false)
    @JoinColumn(name="vente_id",referencedColumnName = "id",insertable = false,updatable = false)
    private Vente vente;
    private LocalDate dateOffre;
    private double montant;
    private String urlJustificatif;
    private boolean offreAcceptee;
    private String commentaire;//

    public Offre() {
    }

    public Offre(Epaviste epaviste, Vente vente, LocalDate dateOffre, double montant, String urlJustificatif, boolean offreAcceptee, String commentaire) {
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
