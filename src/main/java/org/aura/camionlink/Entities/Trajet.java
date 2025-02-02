package org.aura.camionlink.Entities;

import java.time.LocalDate;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String pointDepart;
    private String pointArrivee;
    private LocalDate dateDepart;
    private LocalDate dateArrivee;
    private TrajetStatut statut;

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Conducteur conducteur;
    
    @ManyToOne
    @JoinColumn(name = "camion_id")
    private Camion camion;
}
