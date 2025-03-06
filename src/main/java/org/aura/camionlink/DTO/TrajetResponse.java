package org.aura.camionlink.DTO;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.aura.camionlink.Entities.Enums.TrajetStatut;

public record TrajetResponse(
        Long id,
        String pointDepart,
        String pointArrivee,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
        LocalDateTime dateDepart,

        @JsonFormat(pattern = "yyyy-MM-dd HH:mm", timezone = "UTC")
        LocalDateTime dateArrivee,

        TrajetStatut statut,
        ConducteurResponse conducteur,
        CamionResponse camion,
        CargaisonResponse cargaison
) {}