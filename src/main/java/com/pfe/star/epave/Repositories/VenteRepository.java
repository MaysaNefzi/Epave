package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.RapportPre;
import com.pfe.star.epave.Models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente,Long> {
    @Query(value = "SELECT v from Vente v order by v.dateFin ASC ")
    public List<Vente> findAllByDateFin();

    @Query(value = "SELECT v , o from Vente v  , Offre o where v.id=o.id.venteId and o.id.epavisteId=?1 order by v.dateFin ASC ")
    public List<Vente> Vente_By_Epv( Long idE);

    @Query(value = "SELECT v  from Vente v  where v.sinistre.id=?1   ")
    public Vente Vente_By_Sin(Long sin);

    @Query(value = "SELECT v , o from Vente v  , Offre o where v.sinistre.id=?2 and  v.id=o.id.venteId and o.id.epavisteId=?1 order by v.dateFin ASC ")
    public List<Vente> Vente_By_EpvSin( Long idE , Long idS);

}
