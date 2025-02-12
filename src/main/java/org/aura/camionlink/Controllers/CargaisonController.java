package org.aura.camionlink.Controllers;

import java.util.List;

import org.aura.camionlink.DTO.CargaisonRequest;
import org.aura.camionlink.DTO.CargaisonResponse;
import org.aura.camionlink.Services.Interface.CargaisonService;
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
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/cargaisons")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CargaisonController {
    private final CargaisonService cargaisonService;

    @PostMapping
    public ResponseEntity<CargaisonResponse> createCargaison(@RequestBody CargaisonRequest request) {
        CargaisonResponse response = cargaisonService.createCargaison(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<CargaisonResponse> getCargaisonById(@PathVariable Long id) {
        CargaisonResponse response = cargaisonService.getCargaisonById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CargaisonResponse>> getAllCargaisons() {
        List<CargaisonResponse> responses = cargaisonService.getAllCargaisons();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CargaisonResponse> updateCargaison(
            @PathVariable Long id,
            @RequestBody CargaisonRequest request
    ) {
        CargaisonResponse response = cargaisonService.updateCargaison(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCargaison(@PathVariable Long id) {
        cargaisonService.deleteCargaison(id);
        return ResponseEntity.noContent().build();
    }
}
