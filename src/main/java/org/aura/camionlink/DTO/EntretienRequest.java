package org.aura.camionlink.DTO;

import jakarta.persistence.*;
import org.aura.camionlink.Entities.Enums.EtatEntretien;
import org.aura.camionlink.Entities.Panne;

import java.time.LocalDate;

public record EntretienRequest(
         LocalDate dateEntretien,
         EtatEntretien etatEntretien,
         double cout,
         Long panneId
) {
}
