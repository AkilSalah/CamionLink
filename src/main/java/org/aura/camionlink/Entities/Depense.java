package org.aura.camionlink.Entities;

import java.time.LocalDate;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.aura.camionlink.Entities.Enums.DepenseStatut;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Depense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String typeDepense;
    private double montant;
    private LocalDate date;
    @Enumerated(EnumType.STRING)
    private DepenseStatut statut = DepenseStatut.EN_ATTENTE;
    @ManyToOne
    @JoinColumn(name = "trajet_id")
    private Trajet trajet;
}
