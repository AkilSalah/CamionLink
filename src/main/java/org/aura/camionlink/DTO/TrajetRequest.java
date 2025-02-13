package org.aura.camionlink.DTO;


import java.time.LocalDateTime;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TrajetRequest(
    @NotBlank(message = "Le point de départ est obligatoire")
    String pointDepart,

    @NotBlank(message = "Le point d'arrivée est obligatoire")
    String pointArrivee,

    @NotNull(message = "La date de départ est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "La date de départ doit être dans le futur ou aujourd'hui")
    LocalDateTime dateDepart,

    @NotNull(message = "La date d'arrivée est obligatoire")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @FutureOrPresent(message = "La date d'arrivée doit être dans le futur ou aujourd'hui")
    LocalDateTime dateArrivee,

    @NotNull(message = "Le statut du trajet est obligatoire")
    TrajetStatut statut,

    @NotNull(message = "L'ID du conducteur est obligatoire")
    Long conducteurId,

    @NotNull(message = "L'ID du camion est obligatoire")
    Long camionId,

    @NotNull(message = "L'ID de la cargaison est obligatoire")
    Long cargaisonId
) {}