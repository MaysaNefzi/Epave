package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Vente;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VenteRepository extends JpaRepository<Vente,Long> {
}
