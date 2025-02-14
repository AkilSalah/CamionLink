package org.aura.camionlink.Controllers;

import java.util.List;

import org.aura.camionlink.DTO.DepenseRequest;
import org.aura.camionlink.DTO.DepenseResponse;
import org.aura.camionlink.Entities.Enums.DepenseStatut;
import org.aura.camionlink.Services.Interface.DepenseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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
public class DepenseController {

     private final DepenseService depenseService;

    @GetMapping("/admin/depenses")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<List<DepenseResponse>> getDepenses() {
        List<DepenseResponse> depenses = depenseService.getDepenses();
        return ResponseEntity.ok(depenses);
    }

    @GetMapping("/conducteur/depenses/trajet/{trajetId}")
    @PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_CONDUCTEUR')")
    public ResponseEntity<List<DepenseResponse>> getDepenseByTrajet(@PathVariable long trajetId) {
        List<DepenseResponse> depenses = depenseService.getDepenseByTrajet(trajetId);
        return ResponseEntity.ok(depenses);
    }

    @PostMapping("/conducteur/depenses")
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<DepenseResponse> createDepense(@Valid @RequestBody DepenseRequest request) {
        DepenseResponse depense = depenseService.createDepense(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(depense);
    }

    @PutMapping("/conducteur/depenses/{id}")
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<DepenseResponse> updateDepense(
            @PathVariable long id,
            @Valid @RequestBody DepenseRequest request) {
        DepenseResponse depense = depenseService.updateDepense(request, id);
        return ResponseEntity.ok(depense);
    }

    @DeleteMapping("/conducteur/depenses/{id}")
    @PreAuthorize("hasAuthority('ROLE_CONDUCTEUR')")
    public ResponseEntity<Void> deleteDepense(@PathVariable long id) {
        depenseService.deleteDepense(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("admin/depenses/{id}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<DepenseResponse> validateDepense(@PathVariable long id,
     @RequestParam DepenseStatut statut ){
        DepenseResponse depenseValidated =  depenseService.validateDepense(id, statut);
        return ResponseEntity.ok(depenseValidated);
     }

}
