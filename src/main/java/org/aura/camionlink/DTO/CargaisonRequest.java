package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.CargaisonType;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record CargaisonRequest(
    @NotBlank(message = "La description ne doit pas être vide")
    String description,

    @Positive(message = "Le poids doit être un nombre positif")
    double poids,

    @NotNull(message = "Le type de cargaison est obligatoire")
    CargaisonType type
) {}
