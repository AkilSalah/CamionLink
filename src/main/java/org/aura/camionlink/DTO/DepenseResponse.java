package org.aura.camionlink.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.aura.camionlink.Entities.Enums.DepenseStatut;

@Builder
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
