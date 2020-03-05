package com.pfe.star.epave.Models;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity
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

    public Offre() {
    }

    public Offre(Epaviste epaviste, Vente vente) {
        this.epaviste = epaviste;
        this.vente = vente;
        this.id = new OffreID(epaviste.getCin(), vente.getId());
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
}
