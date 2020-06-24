package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public interface OffreRepository  extends JpaRepository<Offre,Long> {
    @Query(value = "SELECT o from Offre o where o.id.venteId=?1  order by  o.montant DESC ")
    public List<Offre> getOffresoreder(Long id);

    @Query(value = "SELECT count(o) from Offre o where o.id.venteId=?1  ")
    public Integer getnbOffres(Long id);

    @Query(value = "SELECT max(o.montant) from Offre o where o.id.venteId=?1  ")
    public double montantBest(Long id);

    @Query(value = "SELECT o from Offre o where o.id.venteId=?1 and o.id.epavisteId=?2 ")
    public Offre OffreById(Long idV,Long idE);
    @Query(value = "SELECT o from Offre o where o.id.epavisteId=?1 ")
    public Collection<Offre> OffreByEpv(Long idE);

    @Query(value = "SELECT o from Offre o where o.id.venteId=?1 and o.offreAcceptee=true")
    public Offre OffreAcceptee(Long id);
}
