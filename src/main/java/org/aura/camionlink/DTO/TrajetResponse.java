package org.aura.camionlink.DTO;

import java.time.LocalDateTime;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

public record TrajetResponse(
    Long id,
    String pointDepart,
    String pointArrivee,
    LocalDateTime dateDepart,
    LocalDateTime dateArrivee,
    TrajetStatut statut,
    ConducteurResponse conducteur,
    CamionResponse camion,
    CargaisonResponse cargaison
) {}
