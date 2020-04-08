package com.pfe.star.epave.Security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pfe.star.epave.Models.Utilisateur;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

public class JWTAuthentificationFilter extends UsernamePasswordAuthenticationFilter {
    private AuthenticationManager authenticationManager;

    public JWTAuthentificationFilter(AuthenticationManager authenticationManager) {
        super();
        this.authenticationManager=authenticationManager;
    }
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        Utilisateur utilisateur=null;
        try {
            utilisateur= new ObjectMapper().readValue(request.getInputStream(),Utilisateur.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(utilisateur.getUsername(),utilisateur.getPassword()));
    }
    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        User springUser= (User) authResult.getPrincipal();
        String jwt= Jwts.builder()
                .setSubject(springUser.getUsername())
                .setExpiration(new Date(System.currentTimeMillis()+SecurityConstants.EXPIRATION_TIME))
                .signWith(SignatureAlgorithm.HS256,SecurityConstants.SECRET)
                .claim("roles",springUser.getAuthorities())
                .compact();
        response.addHeader(SecurityConstants.HEADER_STRING,SecurityConstants.TOKEN_PREFIX+jwt);
    }
}
