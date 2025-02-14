package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.ConducteurStatut;

import lombok.Builder;


@Builder
public record ConducteurResponse(
     Long id,
     String nom,
     String prenom,
     String email,
     int contact,
     String numeroPermis,
     ConducteurStatut disponibilite
) {}
