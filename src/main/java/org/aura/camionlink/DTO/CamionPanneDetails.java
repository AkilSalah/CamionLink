package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.CamionEtat;

public record CamionPanneDetails(
         Long camionId,
         String marque,
         String modele,
         String etat,
         String descriptionPanne,
         Long nombreEntretiens
) {}
