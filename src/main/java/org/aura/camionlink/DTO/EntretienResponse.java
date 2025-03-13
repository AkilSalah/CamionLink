package org.aura.camionlink.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.aura.camionlink.Entities.Enums.EtatEntretien;

import java.time.LocalDate;

public record EntretienResponse(
        Long id,
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
        LocalDate dateEntretien,
        EtatEntretien etatEntretien,
        double cout,
        PanneResponse panne
) {
}
