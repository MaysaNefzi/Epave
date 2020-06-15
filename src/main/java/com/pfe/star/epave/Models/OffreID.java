package com.pfe.star.epave.Models;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class OffreID implements Serializable {
    @Column(name="epaviste_id")
    private Long epavisteId;
    @Column(name="vente_id")
    private Long venteId;

    public OffreID() {
    }

    public OffreID(Long epavisteId, Long venteId) {
        this.epavisteId = epavisteId;
        this.venteId = venteId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OffreID offreID = (OffreID) o;
        return epavisteId == offreID.epavisteId &&
                venteId == offreID.venteId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(epavisteId, venteId);
    }

    public Long getEpavisteId() {
        return epavisteId;
    }

    public void setEpavisteId(Long epavisteId) {
        this.epavisteId = epavisteId;
    }

    public Long getVenteId() {
        return venteId;
    }

    public void setVenteId(Long venteId) {
        this.venteId = venteId;
    }
}