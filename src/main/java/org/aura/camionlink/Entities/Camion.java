package org.aura.camionlink.Entities;

import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.CamionEtat;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Camion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String marque;
    private String modele;
    private LocalDate annee;
    private CamionEtat etat; 
}
