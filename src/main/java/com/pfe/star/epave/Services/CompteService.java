package com.pfe.star.epave.Services;

import com.pfe.star.epave.Models.Utilisateur;

public interface CompteService {
    public Utilisateur findUserByUSername(String username);
}
