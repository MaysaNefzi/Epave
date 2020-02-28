package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepository  extends JpaRepository<Utilisateur,Long> {
}
