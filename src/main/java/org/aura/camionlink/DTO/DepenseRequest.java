package org.aura.camionlink.DTO;

import java.time.LocalDate;


import lombok.Builder;

import jakarta.validation.constraints.*;

@Builder
public record DepenseRequest(
    @NotBlank(message = "Le type de dépense est obligatoire")
    String typeDepense,

    @Positive(message = "Le montant doit être un nombre positif")
    double montant,

    @NotNull(message = "La date ne peut pas être nulle")
    @PastOrPresent(message = "La date doit être aujourd'hui ou dans le passé")
    LocalDate date,

    @Positive(message = "L'ID du trajet doit être un nombre positif")
    long trajetId
) {}

