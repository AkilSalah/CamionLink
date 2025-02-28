package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.EtatEntretien;

import java.time.LocalDate;

public record EntretienRequest(
         LocalDate dateEntretien,
         EtatEntretien etatEntretien,
         double cout,
         Long panneId
){}
