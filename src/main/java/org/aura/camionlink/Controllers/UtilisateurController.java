package org.aura.camionlink.Controllers;

import java.util.List;

import org.aura.camionlink.DTO.ConducteurResponse;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import org.aura.camionlink.DTO.ValidationGroups;
import org.aura.camionlink.DTO.VerifyPasswordRequest;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Utilisateur;
import org.aura.camionlink.Exceptions.ConducteurException;
import org.aura.camionlink.Repositories.ConducteurRepo;
import org.aura.camionlink.Repositories.UtilisateurRepo;
import org.aura.camionlink.Services.Interface.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UtilisateurController {
    private final UtilisateurService utilisateurService;
    private final UtilisateurRepo utilisateurRepo;
    private final ConducteurRepo conducteurRepo;
    private final PasswordEncoder passwordEncoder;

    @GetMapping("/conducteur")
    @PreAuthorize("hasAnyAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<ConducteurResponse> getConducteurById() {
        Long conducteurId = getConducteurId();
        return ResponseEntity.ok(utilisateurService.getConducteurById(conducteurId));
    }
    @GetMapping("/conducteur/home")
    @PreAuthorize("hasAnyAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<List<ConducteurResponse>> getEliteConducteur() {
        return ResponseEntity.ok(utilisateurService.getEliteConducteur());
    }

    @GetMapping("/admin/conducteurs")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<ConducteurResponse>> getConducteurs() {
        return ResponseEntity.ok(utilisateurService.getConducteurs());
    }

    @GetMapping("/conducteur/conducteurs")
    @PreAuthorize("hasAnyAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<List<ConducteurResponse>> displayConducteurs() {
        return ResponseEntity.ok(utilisateurService.getConducteurs());
    }

    @PutMapping("/conducteur/profile/{id}")
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')") 
    public ResponseEntity<ConducteurResponse> updateConducteur(@PathVariable Long id, @Validated(ValidationGroups.OnUpdate.class) @RequestBody RegisterConducteurRequest request) {
        return ResponseEntity.ok(utilisateurService.updateConducteur(request, id));
    }

    @DeleteMapping("admin/conducteurs/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteConducteur(@PathVariable Long id) {
        utilisateurService.deleteConducteur(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("admin/conducteurs/count")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN')")
    public ResponseEntity<Long> getConducteurCount() {
        return ResponseEntity.ok(utilisateurService.getConducteurCount());
    }

    @DeleteMapping("conducteur/{id}")
    @PreAuthorize("hasAnyAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<Void> deleteAccounte(@PathVariable Long id) {
        Long conducteurId = getConducteurId();
        if (!conducteurId.equals(id)){
            throw new RuntimeException("vous etes pas authorisé");
        }else {
            utilisateurService.deleteConducteur(id);
            return ResponseEntity.noContent().build();
        }
    }

    private long getConducteurId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return utilisateurRepo.findByEmail(email)
                .map(Utilisateur::getId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé pour l'email : " + email));
    }
}