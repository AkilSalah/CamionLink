package org.aura.camionlink.Entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
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
    @Enumerated(EnumType.STRING)
    private TrajetStatut statut;

    @ManyToOne
    @JoinColumn(name = "conducteur_id")
    private Conducteur conducteur;

    @ManyToOne
    @JoinColumn(name = "camion_id")
    private Camion camion;

    @OneToMany(mappedBy = "trajet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Depense> depenses = new ArrayList<>();
    
    @OneToOne
    @JoinColumn(name = "cargaison_id")
    private Cargaison cargaison;

}
