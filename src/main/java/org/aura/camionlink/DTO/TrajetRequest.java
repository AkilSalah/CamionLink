package org.aura.camionlink.DTO;


import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.aura.camionlink.Entities.Enums.TrajetStatut;

import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record TrajetRequest(
        @NotBlank(message = "Le point de départ est obligatoire")
        @JsonProperty("point_depart")
        String pointDepart,

        @NotBlank(message = "Le point d'arrivée est obligatoire")
        @JsonProperty("point_arrivee")
        String pointArrivee,

        @NotNull(message = "La date de départ est obligatoire")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @FutureOrPresent(message = "La date de départ doit être dans le futur ou aujourd'hui")
        @JsonProperty("date_depart")
        LocalDateTime dateDepart,

        @NotNull(message = "La date d'arrivée est obligatoire")
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
        @FutureOrPresent(message = "La date d'arrivée doit être dans le futur ou aujourd'hui")
        @JsonProperty("date_arrivee")
        LocalDateTime dateArrivee,

        @NotNull(message = "Le statut du trajet est obligatoire")
        @JsonProperty("statut")
        TrajetStatut statut,

        @NotNull(message = "L'ID du conducteur est obligatoire")
        @JsonProperty("conducteur_id")
        Long conducteurId,

        @NotNull(message = "L'ID du camion est obligatoire")
        @JsonProperty("camion_id")
        Long camionId,

        @NotNull(message = "L'ID de la cargaison est obligatoire")
        @JsonProperty("cargaison_id")
        Long cargaisonId
) {}