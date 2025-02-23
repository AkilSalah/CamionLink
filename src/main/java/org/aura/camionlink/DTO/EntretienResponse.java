package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.EtatEntretien;

import java.time.LocalDate;

public record EntretienResponse(
        Long id,
        LocalDate dateEntretien,
        EtatEntretien etatEntretien,
        double cout,
        PanneResponse panne
) {
}
