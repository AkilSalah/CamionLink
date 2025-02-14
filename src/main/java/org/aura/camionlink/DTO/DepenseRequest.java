package org.aura.camionlink.DTO;

import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.DepenseStatut;

import lombok.Builder;

@Builder
public record DepenseRequest(
    String typeDepense,
    double montant,
    LocalDate date,
    long trajetId,
    DepenseStatut statut
){} 
