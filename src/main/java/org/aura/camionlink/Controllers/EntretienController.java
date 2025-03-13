package org.aura.camionlink.Controllers;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aura.camionlink.DTO.EntretienRequest;
import org.aura.camionlink.DTO.EntretienResponse;
import org.aura.camionlink.Services.Interface.EntretienService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/")
@AllArgsConstructor
@Slf4j
public class EntretienController {

    private final EntretienService entretienService;

    @PostMapping("admin/entretiens")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EntretienResponse> createEntretien(@RequestBody EntretienRequest request) {
        log.info("entretien request: {}", request);
        EntretienResponse response = entretienService.createEntretien(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("conducteur/entretiens/{id}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<EntretienResponse> getEntretien(@PathVariable long id) {
        return ResponseEntity.ok(entretienService.getEntretien(id));
    }

    @GetMapping("conducteur/entretiens")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_CONDUCTEUR')")
    public ResponseEntity<List<EntretienResponse>> getAllEntretiens() {
        return ResponseEntity.ok(entretienService.getAllEntretien());
    }

    @PutMapping("admin/entretiens/{id}")
    public ResponseEntity<EntretienResponse> updateEntretien(@RequestBody EntretienRequest request, @PathVariable long id) {
        return ResponseEntity.ok(entretienService.updateEntretien(request, id));
    }

    @DeleteMapping("admin/entretiens/{id}")
    public ResponseEntity<Void> deleteEntretien(@PathVariable long id) {
        entretienService.deleteEntretien(id);
        return ResponseEntity.noContent().build();
    }
}
