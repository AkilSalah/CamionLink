package org.aura.camionlink.Controllers;

import java.util.List;

import org.aura.camionlink.DTO.CamionRequest;
import org.aura.camionlink.DTO.CamionResponse;
import org.aura.camionlink.Services.Interface.CamionService;
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
@RequestMapping("/api/admin/camions")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class CamionController {
    private final CamionService camionService;
    @PostMapping
    public ResponseEntity<CamionResponse> addCamion(@RequestBody CamionRequest camionRequest) {
        CamionResponse response = camionService.addCamion(camionRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<CamionResponse> updateCamion(@PathVariable Long id, @RequestBody CamionRequest camionRequest) {
        CamionResponse response = camionService.updateCamion(id, camionRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCamion(@PathVariable Long id) {
        camionService.deleteCamion(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}")
    public ResponseEntity<CamionResponse> getCamionById(@PathVariable Long id) {
        CamionResponse response = camionService.getCamionById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<CamionResponse>> getAllCamions() {
        List<CamionResponse> responses = camionService.getAllCamions();
        return ResponseEntity.ok(responses);
    }

}
