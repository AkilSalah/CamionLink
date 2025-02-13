package org.aura.camionlink.DTO;


import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

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
    @FutureOrPresent(message = "La date de départ doit être dans le futur ou aujourd'hui")
    LocalDate dateDepart,

    @NotNull(message = "La date d'arrivée est obligatoire")
    @FutureOrPresent(message = "La date d'arrivée doit être dans le futur ou aujourd'hui")
    LocalDate dateArrivee,

    @NotNull(message = "Le statut du trajet est obligatoire")
    TrajetStatut statut,

    @NotNull(message = "L'ID du conducteur est obligatoire")
    Long conducteur_id,

    @NotNull(message = "L'ID du camion est obligatoire")
    Long camion_id,

    @NotNull(message = "L'ID de la cargaison est obligatoire")
    Long cargaison_id
) {}
