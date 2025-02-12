package org.aura.camionlink.DTO;

import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.CamionEtat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import lombok.Builder;


@Builder
public record CamionRequest(
    @NotBlank(message = "La marque ne peut pas être vide") 
    String marque,

    @NotBlank(message = "Le modèle ne peut pas être vide") 
    String modele,

    @NotNull(message = "L'année ne peut pas être nulle") 
    @Past(message = "L'année doit être dans le passé") 
    LocalDate annee,

    @NotNull(message = "L'état ne peut pas être nul") 
    CamionEtat etat
) {
}