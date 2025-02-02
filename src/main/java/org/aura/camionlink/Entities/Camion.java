package org.aura.camionlink.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.aura.camionlink.Entities.Enums.CamionEtat;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "camion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trajet> trajets = new ArrayList<>(); 
    @OneToMany(mappedBy = "camion", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Entretien> entretiens = new ArrayList<>();
}
