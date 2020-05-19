package com.pfe.star.epave.Models;

import javax.persistence.*;
import java.util.Date;

@Entity
public class ConfirmationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long tokenid;
    private String confirmationToken;
    private Date createdDate;
    private Date expiredDate;
    @OneToOne//(targetEntity = Client.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "Client_id")
    private Client client;
    private Long idClient;

    public ConfirmationToken(String confirmationToken, Date createdDate, Date expiredDate, Client client) {
        this.confirmationToken = confirmationToken;
        this.createdDate = createdDate;
        this.expiredDate = expiredDate;
        this.client = client;
    }

    public ConfirmationToken() {
    }

    public long getTokenid() {
        return tokenid;
    }

    public void setTokenid(long tokenid) {
        this.tokenid = tokenid;
    }

    public String getConfirmationToken() {
        return confirmationToken;
    }

    public void setConfirmationToken(String confirmationToken) {
        this.confirmationToken = confirmationToken;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Date getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(Date expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Client getClient() {
        return client;
    }

    public void setClient(Client client) {
        this.client = client;
    }

    public Long getIdClient() {
        return idClient;
    }

    public void setIdClient(Long idClient) {
        this.idClient = idClient;
    }
}
