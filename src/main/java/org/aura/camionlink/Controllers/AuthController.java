package org.aura.camionlink.Controllers;

import org.aura.camionlink.DTO.AuthenticationResponse;
import org.aura.camionlink.DTO.LoginRequest;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import org.aura.camionlink.DTO.VerifyPasswordRequest;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Exceptions.ConducteurException;
import org.aura.camionlink.Repositories.ConducteurRepo;
import org.aura.camionlink.Services.Interface.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
@Slf4j
public class AuthController {

    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final ConducteurRepo conducteurRepo;

    @PostMapping("/register/conducteur")
    public ResponseEntity<AuthenticationResponse> registerConducteur(@RequestBody RegisterConducteurRequest request) {
        log.info("Tentative d'enregistrement d'un conducteur: {}", request.email());
        return ResponseEntity.ok(authService.registerConducteur(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        log.info("Tentative de connexion pour: {}", request.getEmail());
        return ResponseEntity.ok(authService.login(request));
    }
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestHeader(name = "Authorization") String token ) {
        String jwt = token.substring(7);
        authService.AddToken(jwt);
        return ResponseEntity.ok().body("Successfully logged out");
    }

    @PostMapping("/verify-password")
    public boolean verifyPassword(@RequestBody VerifyPasswordRequest request) {
        log.info("Tentative de vérification du mot de passe pour l'utilisateur: {}", request.getId());
        Conducteur conducteur = conducteurRepo.findById(request.getId())
                .orElseThrow(() -> {
                    log.error("Utilisateur non trouvé avec l'ID: {}", request.getId());
                    return new ConducteurException(request.getId());
                });

        boolean isPasswordValid = passwordEncoder.matches(request.getCurrentPassword(), conducteur.getPassword());
        log.info("Résultat de la vérification du mot de passe: {}", isPasswordValid);
        return isPasswordValid;
    }

}

