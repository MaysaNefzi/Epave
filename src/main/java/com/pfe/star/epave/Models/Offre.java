package com.pfe.star.epave.Models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.bind.DefaultValue;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

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
    //    @NotNull
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime dateOffre;
    @NotNull
    private double montant;
    @NotNull
    private String urlJustificatif;
    @NotNull
    private boolean offreAcceptee =false ;

    @NotNull
    private Integer clientAccepte =0 ;//1 : accpete     -1 : refuse
    private String commentaire;

    public Offre() {
    }

    public Offre(Epaviste epaviste, Vente vente, Integer clientAccepte,LocalDateTime dateOffre, double montant, String urlJustificatif, boolean offreAcceptee, String commentaire) {
        this.epaviste = epaviste;
        this.vente = vente;
        this.dateOffre = dateOffre;
        this.montant = montant;
        this.urlJustificatif = urlJustificatif;
        this.offreAcceptee = offreAcceptee;
        this.commentaire = commentaire;
        this.clientAccepte=clientAccepte;
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

    public LocalDateTime getDateOffre() {
        return dateOffre;
    }

    public void setDateOffre(LocalDateTime dateOffre) {
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

    public Integer getClientAccepte() {
        return clientAccepte;
    }

    public void setClientAccepte(Integer clientAccepte) {
        this.clientAccepte = clientAccepte;
    }

}