package org.aura.camionlink.Services.Implementation;


import java.util.ArrayList;
import java.util.List;

import org.aura.camionlink.DTO.AuthenticationResponse;
import org.aura.camionlink.DTO.LoginRequest;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Utilisateur;
import org.aura.camionlink.Mapper.ConducteurMapper;
import org.aura.camionlink.Repositories.UtilisateurRepo;
import org.aura.camionlink.Security.JwtService;
import org.aura.camionlink.Services.Interface.AuthService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService {
    private final UtilisateurRepo userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final ConducteurMapper conducteurMapper;
     List<String> BlackTokens = new ArrayList<>();


    @Override
    public AuthenticationResponse registerConducteur(RegisterConducteurRequest request) {
        log.info("Requête reçue: {}", request);
        
        try {
            log.info("Début du mapping");
            
            Conducteur conducteur = conducteurMapper.toEntity(request);
            
            log.info("Mapping réussi: {}", conducteur);
            
            conducteur.setPassword(passwordEncoder.encode(request.password()));
            
            log.info("Tentative de sauvegarde");
            Conducteur savedConducteur = userRepository.save(conducteur);
            log.info("Sauvegarde réussie avec ID: {}", savedConducteur.getId());
            
            String token = jwtService.generateToken(savedConducteur);
            
            return AuthenticationResponse.builder()
                    .token(token)
                    .type("CONDUCTEUR")
                    .userId(savedConducteur.getId())
                    .build();
                    
        } catch (Exception e) {
            log.error("Erreur complète: ", e);
            throw new RuntimeException("Erreur lors de l'enregistrement: " + e.getMessage());
        }
    }

    @Override
    public AuthenticationResponse login(LoginRequest request) {
        try {
            Utilisateur user = userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
                throw new RuntimeException("Mot de passe incorrect");
            }

            String token = jwtService.generateToken(user);
            String type = user instanceof Conducteur ? "CONDUCTEUR" : "ADMIN";

            return AuthenticationResponse.builder()
                    .token(token)
                    .type(type)
                    .userId(user.getId())
                    .build();
        } catch (Exception e) {
            log.error("Erreur lors de la connexion: ", e);
            throw new RuntimeException(e.getMessage());
        }
    }
    @Override
    public void AddToken(String token) {
        BlackTokens.add(token);
    }

    @Override
    public Boolean GetTokens(String token) {
        return BlackTokens.contains(token);
    }
}

