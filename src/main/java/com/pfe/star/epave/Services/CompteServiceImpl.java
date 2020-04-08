package com.pfe.star.epave.Services;

import com.pfe.star.epave.Models.Utilisateur;
import com.pfe.star.epave.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CompteServiceImpl implements CompteService {
    @Autowired
    private UtilisateurRepository u_repo;
    @Override
    public Utilisateur findUserByUSername(String username) {
        return u_repo.findByUsername(username);
    }

}