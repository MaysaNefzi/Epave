package com.pfe.star.epave.Repositories;

import com.pfe.star.epave.Models.Offre;
import com.pfe.star.epave.Models.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UtilisateurRepository  extends JpaRepository<Utilisateur,Long> {
    public Utilisateur findByUsername(String username);

}
