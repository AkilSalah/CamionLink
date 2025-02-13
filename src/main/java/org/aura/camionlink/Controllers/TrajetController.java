package org.aura.camionlink.Controllers;

import java.util.List;

import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;
import org.aura.camionlink.Services.Interface.TrajetService;
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

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/admin/trajets")
@PreAuthorize("hasRole('ADMIN')")
@RequiredArgsConstructor
public class TrajetController {
      private final TrajetService trajetService;

    @PostMapping
    public ResponseEntity<TrajetResponse> createTrajet(@RequestBody TrajetRequest request) {
        System.out.println("test : " +request);
        TrajetResponse response = trajetService.createTrajet(request);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrajetResponse> getTrajetById(@Valid @PathVariable Long id) {
        TrajetResponse response = trajetService.getTrajetById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<TrajetResponse>> getAllTrajets() {
        List<TrajetResponse> responses = trajetService.getAllTrajets();
        return ResponseEntity.ok(responses);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrajetResponse> updateTrajet(
            @Valid 
            @PathVariable Long id,
            @RequestBody TrajetRequest request
    ) {
        TrajetResponse response = trajetService.updateTrajet(id, request);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrajet(@PathVariable Long id) {
        trajetService.deleteTrajet(id);
        return ResponseEntity.noContent().build();
    }

}
