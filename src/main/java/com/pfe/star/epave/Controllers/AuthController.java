package com.pfe.star.epave.Controllers;

import com.pfe.star.epave.Models.ERole;
import com.pfe.star.epave.Models.Role;
import com.pfe.star.epave.Models.Utilisateur;
import com.pfe.star.epave.Repositories.RoleRepository;
import com.pfe.star.epave.Repositories.UtilisateurRepository;
import com.pfe.star.epave.Security.Jwt.JwtUtils;
import com.pfe.star.epave.Security.Payload.Request.LoginRequest;
import com.pfe.star.epave.Security.Payload.Request.SignupRequest;
import com.pfe.star.epave.Security.Payload.Response.JwtResponse;
import com.pfe.star.epave.Security.Payload.Response.MessageResponse;
import com.pfe.star.epave.Security.Services.UserDetailsImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/authentif")

public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    private final Logger log = LoggerFactory.getLogger(UtilisateurController.class);


    @Autowired
    UtilisateurRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    JwtUtils jwtUtils;

    @ResponseBody
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());


        return ResponseEntity.ok(new JwtResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getPassword(),
                roles));
    }

    @PostMapping("/signup")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signUpRequest) {

        if (userRepository.existsByUsername(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        Utilisateur utilisateur=new Utilisateur(signUpRequest.getCin(),
                signUpRequest.getUsername(),
                encoder.encode((signUpRequest.getPassword())),
                signUpRequest.getNom(),
                signUpRequest.getPrenom());

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();

        if (strRoles != null){
            strRoles.forEach(role -> {

                if(role=="client"){
                        Role clientRole = roleRepository.findByName(ERole.ROLE_CLT)
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(clientRole);

                    utilisateur.setRoles(roles);
                    userRepository.save(utilisateur);
                }
            });
        }
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }
}
