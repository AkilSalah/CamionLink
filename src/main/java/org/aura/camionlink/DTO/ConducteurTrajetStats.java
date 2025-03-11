package org.aura.camionlink.DTO;

public record ConducteurTrajetStats(
         String nom,
         String prenom,
         String email,
         Long conducteurId,
         Long nombreTrajets
) {
}
