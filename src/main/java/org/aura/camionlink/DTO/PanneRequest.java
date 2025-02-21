package org.aura.camionlink.DTO;

import lombok.Builder;
import org.aura.camionlink.Entities.Enums.UrgencePanne;

import java.time.LocalDate;

@Builder
public record PanneRequest (
        long camionId,
        LocalDate datePanne,
        String description,
        UrgencePanne urgence
){}
