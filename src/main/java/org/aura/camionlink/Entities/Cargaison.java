package org.aura.camionlink.Entities;

import org.aura.camionlink.Entities.Enums.CargaisonType;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Data;
import org.aura.camionlink.Entities.Enums.StatutCargaison;

@Entity
@Data
public class Cargaison {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String description;
    private double poids;
    @Enumerated(EnumType.STRING)
    private CargaisonType type;
    @Enumerated(EnumType.STRING)
    private StatutCargaison cargaisonStatut = StatutCargaison.EN_COURS;
    
    @OneToOne(mappedBy = "cargaison")
    private Trajet trajet;
}
