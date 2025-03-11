package org.aura.camionlink.Controllers;

import java.util.List;

import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;
import org.aura.camionlink.Entities.Enums.TrajetStatut;
import org.aura.camionlink.Entities.Utilisateur;
import org.aura.camionlink.Repositories.UtilisateurRepo;
import org.aura.camionlink.Services.Interface.TrajetService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class TrajetController {
      private final TrajetService trajetService;
      private final UtilisateurRepo utilisateurRepo;

    @PostMapping("/admin/trajets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrajetResponse> createTrajet(@RequestBody TrajetRequest request) {
        System.out.println("test : " +request);
        TrajetResponse response = trajetService.createTrajet(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("admin/trajets/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrajetResponse> getTrajetById(@Valid @PathVariable Long id) {
        TrajetResponse response = trajetService.getTrajetById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping("admin/trajets")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<List<TrajetResponse>> getAllTrajets() {
        List<TrajetResponse> responses = trajetService.getAllTrajets();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("admin/trajets/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<TrajetResponse> updateTrajet(
            @Valid 
            @PathVariable Long id,
            @RequestBody TrajetRequest request
    ) {
        TrajetResponse response = trajetService.updateTrajet(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("admin/trajets/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        trajetService.deleteTrajet(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("conducteur/{id}/trajets")
    @PreAuthorize("hasRole('ROLE_CONDUCTEUR')")
    public ResponseEntity<List<TrajetResponse>> getConducteurTrajets(@PathVariable Long id) {
        Long conduteurId = getConducteurId();
        if (conduteurId != id) {
            return new ResponseEntity<>(HttpStatus.FORBIDDEN);
        }
        List<TrajetResponse> trajets = trajetService.getConducteurTrajets(id);
        return ResponseEntity.ok(trajets);
    }

    @PatchMapping("conducteur/{conducteurId}/trajets/{trajetId}/statut")
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<TrajetResponse> updateStatutTrajet(
            @PathVariable Long conducteurId,
            @PathVariable Long trajetId,
            @RequestParam TrajetStatut statut) {
        TrajetResponse updatedTrajet = trajetService.updateTrajetStatus(conducteurId,trajetId, statut);
        return ResponseEntity.ok(updatedTrajet);
    }

    private long getConducteurId() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return utilisateurRepo.findByEmail(email)
                .map(Utilisateur::getId)
                .orElseThrow(() -> new UsernameNotFoundException("Utilisateur non trouvé pour l'email : " + email));
    }

}
