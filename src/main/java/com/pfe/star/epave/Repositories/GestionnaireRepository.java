package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Gestionnaire;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GestionnaireRepository  extends JpaRepository<Gestionnaire,Long> {

}
