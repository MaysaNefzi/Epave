package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OffreRepository  extends JpaRepository<Offre,Long> {
    @Query(value = "SELECT o from Offre o where o.id.venteId=?1  order by  o.montant DESC ")
    public List<Offre> getOffresoreder(Long id);

    @Query(value = "SELECT o from Offre o where o.id.venteId=?1 and o.id.epavisteId=?2 ")
    public List<Offre> OffreById(Long idV,Long idE);
}
