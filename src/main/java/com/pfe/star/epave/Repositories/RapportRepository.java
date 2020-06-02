package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Rapport;
import com.pfe.star.epave.Models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RapportRepository  extends JpaRepository<Rapport,Long> {
    @Query(value = "SELECT rp ,rf from Rapport rp , RapportFinal rf where rp.id <> rf.id  ")
    public List<Rapport> listeRapport();
}
