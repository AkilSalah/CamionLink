package org.aura.camionlink.Entities;

import org.aura.camionlink.Entities.Enums.CargaisonType;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Entity
@Data
public class Cargaison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private double poids;
    private CargaisonType type;
}
