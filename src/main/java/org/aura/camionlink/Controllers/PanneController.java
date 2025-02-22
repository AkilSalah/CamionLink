package org.aura.camionlink.Controllers;

import lombok.AllArgsConstructor;
import org.aura.camionlink.DTO.PanneRequest;
import org.aura.camionlink.DTO.PanneResponse;
import org.aura.camionlink.Entities.Utilisateur;
import org.aura.camionlink.Repositories.UtilisateurRepo;
import org.aura.camionlink.Services.Interface.PanneService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class PanneController {

    private final PanneService panneService;
    private final UtilisateurRepo userRepo;


    @PostMapping("conducteur/pannes")
    @PreAuthorize("hasRole('ROLE_CONDUCTEUR')")
    public ResponseEntity<PanneResponse> createPanne(@RequestBody PanneRequest panneRequest) {
        long conducteurId = getConducteurId();
        PanneResponse panneResponse = panneService.createPanne(panneRequest, conducteurId);
        return ResponseEntity.status(HttpStatus.CREATED).body(panneResponse);
    }

    @DeleteMapping("conducteur/pannes/{panneId}")
    @PreAuthorize("hasRole('ROLE_CONDUCTEUR')")
    public ResponseEntity<Void> deletePanne(@PathVariable Long panneId) {
        long conducteurId = getConducteurId();
        panneService.deletePanne(panneId, conducteurId);
        return ResponseEntity.noContent().build();
    }


    @GetMapping("/admin/pannes")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<PanneResponse>> getAllPannes() {
        List<PanneResponse> panneResponses = panneService.getAllPanne();
        return ResponseEntity.ok(panneResponses);
    }

    @GetMapping("admin/pannes/{panneId}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<PanneResponse> getPanneById(@PathVariable Long panneId) {
        PanneResponse panneResponse = panneService.getPanneById(panneId);
        return ResponseEntity.ok(panneResponse);
    }

    @GetMapping("conducteur/pannes/trajet/{trajetId}")
    @PreAuthorize("hasRole('ROLE_CONDUCTEUR')")
    public ResponseEntity<List<PanneResponse>> getTrajetPanne(@PathVariable long trajetId) {
        long conducteurId = getConducteurId();
        List<PanneResponse> panneResponses = panneService.getTrajetPanne(trajetId,conducteurId);
        return ResponseEntity.ok(panneResponses);
    }

    private long getConducteurId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepo.findByEmail(email)
                .map(Utilisateur::getId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé pour l'email : " + email));
    }
}
