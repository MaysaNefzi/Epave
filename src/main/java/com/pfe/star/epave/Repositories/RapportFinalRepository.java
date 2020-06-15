package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.RapportFinal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportFinalRepository  extends JpaRepository<RapportFinal,Long> {
    @Query(value = "SELECT r from RapportFinal r where UPPER(r.nomEpaviste) LIKE CONCAT('%',UPPER(:nom_prenom),'%') or" +
            " UPPER(r.prenomEpaviste) LIKE CONCAT('%',UPPER(:nom_prenom),'%')")
    public List<RapportFinal> Rapport_By_Epv(String nom_prenom);
}
