package org.aura.camionlink.DTO;

import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.CamionEtat;

import lombok.Builder;

@Builder
public record CamionRequest(
    String marque,
    String modele,
    LocalDate annee,
    CamionEtat etat
) {
}
