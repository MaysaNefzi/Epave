
package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Gestionnaire;
import com.pfe.star.epave.Models.Offre;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GestionnaireRepository  extends JpaRepository<Gestionnaire,Long> {
    @Query(value = "SELECT g from Gestionnaire g order by g.nom DESC ")
    public List<Gestionnaire> getAllGest();
    Boolean existsByUsername(String username);
    Boolean existsByEmail(String email);
}
