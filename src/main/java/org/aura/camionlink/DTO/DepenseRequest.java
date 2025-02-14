package org.aura.camionlink.DTO;

import java.time.LocalDate;


import lombok.Builder;

@Builder
public record DepenseRequest(
    String typeDepense,
    double montant,
    LocalDate date,
    long trajetId
){} 
