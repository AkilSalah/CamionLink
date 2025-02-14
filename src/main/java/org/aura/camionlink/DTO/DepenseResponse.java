package org.aura.camionlink.DTO;

import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.DepenseStatut;

public record DepenseResponse(
    long id,
    String typeDepense,
    double montant,
    LocalDate date,
    TrajetResponse trajet,
    DepenseStatut statut
) {
}
