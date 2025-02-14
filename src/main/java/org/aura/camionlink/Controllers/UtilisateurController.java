package org.aura.camionlink.Controllers;

import java.util.List;

import org.aura.camionlink.DTO.ConducteurResponse;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import org.aura.camionlink.Services.Interface.UtilisateurService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UtilisateurController {
    private final UtilisateurService utilisateurService;

    @GetMapping("/admin/conducteurs")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<List<ConducteurResponse>> getConducteurs() {
        return ResponseEntity.ok(utilisateurService.getConducteurs());
    }

    @PutMapping("/conducteur/profile/{id}")
    @PreAuthorize("hasRole('CONDUCTEUR')")
    public ResponseEntity<ConducteurResponse> updateConducteur(@PathVariable Long id, @Valid @RequestBody RegisterConducteurRequest request) {
        return ResponseEntity.ok(utilisateurService.updateConducteur(request, id)) ;
    }

    @DeleteMapping("admin/conducteur/{id}")
    @PreAuthorize("hasRole('Admin')")
    public ResponseEntity<Void> deleteConducteur(@PathVariable Long id) {
         utilisateurService.deleteConducteur(id);
         return ResponseEntity.noContent().build();
    }
}
