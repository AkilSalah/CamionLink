package org.aura.camionlink.DTO;


import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

import lombok.Builder;

@Builder
public record TrajetRequest(
         String pointDepart,
         String pointArrivee,
         LocalDate dateDepart,
         LocalDate dateArrivee,
         TrajetStatut statut,
         Long conducteur_id,
         Long camion_id,
         Long cargaison_id
) {}
