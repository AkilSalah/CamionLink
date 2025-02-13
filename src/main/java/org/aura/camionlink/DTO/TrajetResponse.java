package org.aura.camionlink.DTO;

import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

public record TrajetResponse(
    Long id,
    String pointDepart,
    String pointArrivee,
    LocalDate dateDepart,
    LocalDate dateArrivee,
    TrajetStatut statut,
    ConducteurResponse conducteur,
    CamionResponse camion,
    CargaisonResponse cargaison
) {}
