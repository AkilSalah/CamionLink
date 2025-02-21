package org.aura.camionlink.DTO;

import jakarta.persistence.*;
import lombok.Builder;
import org.aura.camionlink.Entities.Camion;
import org.aura.camionlink.Entities.Entretien;
import org.aura.camionlink.Entities.Enums.UrgencePanne;

import java.time.LocalDate;

@Builder
public record PanneResponse (
        Long id,
        CamionResponse camion,
        LocalDate datePanne,
        String description,
        UrgencePanne urgence
        ){
}
