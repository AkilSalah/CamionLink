package org.aura.camionlink.DTO;

import lombok.Builder;
import org.aura.camionlink.Entities.Enums.UrgencePanne;

import java.time.LocalDate;

@Builder
public record PanneResponse (
        Long id,
        TrajetResponse trajet,
        LocalDate datePanne,
        String description,
        UrgencePanne urgence
        ){ }
