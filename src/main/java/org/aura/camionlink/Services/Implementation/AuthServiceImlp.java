package org.aura.camionlink.Services.Implementation;

import org.aura.camionlink.DTO.UtilisateurDTO;
import org.aura.camionlink.Entities.Admin;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Utilisateur;
import org.aura.camionlink.Repositories.UtilisateurRepo;
import org.aura.camionlink.Security.JwtService;
import org.aura.camionlink.Services.Interface.AuthService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class AuthServiceImlp implements AuthService {

    private final UtilisateurRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @Override
    public UtilisateurDTO register(UtilisateurDTO userDTO) {
        Utilisateur user;
        if ("ADMIN".equals(userDTO.getUserType())) {
            user = new Admin();
        } else if ("CONDUCTEUR".equals(userDTO.getUserType())) {
            Conducteur conducteur = new Conducteur();
            conducteur.setNumeroPermis(userDTO.getNumeroPermis());
            conducteur.setDisponibilite(userDTO.getDisponibilite());
            user = conducteur;
        } else {
            throw new IllegalArgumentException("Invalid user type");
        }

        user.setNom(userDTO.getNom());
        user.setPrenom(userDTO.getPrenom());
        user.setEmail(userDTO.getEmail());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setContact(userDTO.getContact());

        userRepository.save(user);

        String jwtToken = jwtService.generateToken(user);
        userDTO.setToken(jwtToken);
        userDTO.setId(user.getId());
        return userDTO;
    }

    @Override
    public UtilisateurDTO login(UtilisateurDTO userDTO) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        userDTO.getEmail(),
                        userDTO.getPassword()
                )
        );

        Utilisateur user = userRepository.findByName(userDTO.getNom())
                .orElseThrow(() -> new RuntimeException("User not found"));

        String jwtToken = jwtService.generateToken(user);
        userDTO.setToken(jwtToken);
        userDTO.setId(user.getId()); 
        return userDTO;
    }
}