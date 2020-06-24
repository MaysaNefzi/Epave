package com.pfe.star.epave.Security.Payload.Request;
import javax.validation.constraints.NotBlank;
public class EngagementRequest {
    @NotBlank
    private Integer clienAccepte;
    private String urlEngagement;

    public Integer getClienAccepte() {
        return clienAccepte;
    }

    public void setClienAccepte(Integer clienAccepte) {
        this.clienAccepte = clienAccepte;
    }

    public String getUrlEngagement() {
        return urlEngagement;
    }

    public void setUrlEngagement(String urlEngagement) {
        this.urlEngagement = urlEngagement;
    }
}
