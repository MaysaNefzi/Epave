package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Epaviste;
import com.pfe.star.epave.Models.Gestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EpavisteRepository extends JpaRepository<Epaviste,Long> {
    @Query(value = "SELECT e from Epaviste e order by e.nom DESC ")
    public List<Epaviste> getAllEpv();
}
