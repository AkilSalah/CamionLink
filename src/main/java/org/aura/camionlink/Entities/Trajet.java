package org.aura.camionlink.Entities;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.aura.camionlink.Entities.Enums.TrajetStatut;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Data;


@Entity
@Data
@Table(name = "trajets")
public class Trajet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false)
    private String pointDepart;
    
    @Column(nullable = false)
    private String pointArrivee;
    
    @Column(nullable = false)
    private LocalDateTime dateDepart;
    
    @Column(nullable = false)
    private LocalDateTime dateArrivee;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TrajetStatut statut;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "conducteur_id", nullable = false)
    private Conducteur conducteur;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "camion_id", nullable = false)
    private Camion camion;
    
    @OneToMany(mappedBy = "trajet", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Depense> depenses = new ArrayList<>();
    
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cargaison_id", nullable = false)
    private Cargaison cargaison;
}