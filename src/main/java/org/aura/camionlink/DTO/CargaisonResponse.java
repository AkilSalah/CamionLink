package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.CargaisonType;
import org.aura.camionlink.Entities.Enums.StatutCargaison;


public record CargaisonResponse(
     Long id,
     String description,
     double poids,
     CargaisonType type,
     StatutCargaison cargaisonStatut
) {}
