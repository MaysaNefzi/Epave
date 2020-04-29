package com.pfe.star.epave.Repositories;


import com.pfe.star.epave.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface UtilisateurRepository  extends JpaRepository<Utilisateur,Long> {
    Optional<Utilisateur> findByUsername(String username);
    Boolean existsByUsername(String username);


}
