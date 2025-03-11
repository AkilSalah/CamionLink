package org.aura.camionlink.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.aura.camionlink.Entities.Enums.DepenseStatut;

public record DepenseResponse(
    long id,
    String typeDepense,
    double montant,
    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
    LocalDate date,
    TrajetResponse trajet,
    DepenseStatut statut
) {
}
