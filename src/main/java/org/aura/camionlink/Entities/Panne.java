package org.aura.camionlink.Entities;

import jakarta.persistence.*;
import lombok.Data;
import org.aura.camionlink.Entities.Enums.UrgencePanne;

import java.time.LocalDate;

@Entity
@Data
public class Panne {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "camion_id", nullable = false)
    private Camion camion;

    private LocalDate datePanne;
    private String description;

    @Enumerated(EnumType.STRING)
    private UrgencePanne urgence;

    @OneToOne(mappedBy = "panne")
    private Entretien entretien;
}