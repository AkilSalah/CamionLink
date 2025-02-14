package org.aura.camionlink.DTO;

import java.time.LocalDate;

public record DepenseResponse(
    long id,
    String typeDepense,
    double montant,
    LocalDate date,
    TrajetResponse trajet
) {
}
