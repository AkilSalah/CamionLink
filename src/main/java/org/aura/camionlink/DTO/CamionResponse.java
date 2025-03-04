package org.aura.camionlink.DTO;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import org.aura.camionlink.Entities.Enums.CamionEtat;

import lombok.Builder;

@Builder
public record CamionResponse(
    long id,
    String marque,
    String modele,
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm", shape = JsonFormat.Shape.STRING)
    LocalDate annee,
    CamionEtat etat
) {
} 
