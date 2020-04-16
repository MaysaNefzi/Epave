package com.pfe.star.epave.Security.Services;

import com.pfe.star.epave.Models.Utilisateur;
import com.pfe.star.epave.Repositories.UtilisateurRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    UtilisateurRepository utilisateurRepository;
    @Override
    @Transactional
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Utilisateur utilisateur=utilisateurRepository.findByUsername(username)
                .orElseThrow(()-> new UsernameNotFoundException("L'utilisateur "+username+" n'est pas trouvé!"));
        return UserDetailsImpl.build(utilisateur);
    }
}
