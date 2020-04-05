package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Repository
public interface VenteRepository extends JpaRepository<Vente,Long> {
    @Query(value = "SELECT v from Vente v where v.dateFin<?1 ")
    public List<Offre> taritement(LocalDateTime now);
}
