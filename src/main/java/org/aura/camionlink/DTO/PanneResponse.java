package org.aura.camionlink.DTO;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import org.aura.camionlink.Entities.Enums.UrgencePanne;

import java.time.LocalDate;

@Builder
public record PanneResponse (
        Long id,
        TrajetResponse trajet,
        @JsonFormat(pattern = "yyyy-MM-dd", timezone = "UTC")
        LocalDate datePanne,
        String description,
        UrgencePanne urgence
){}
