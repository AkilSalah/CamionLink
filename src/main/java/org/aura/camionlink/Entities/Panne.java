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
    @JoinColumn(name = "trajet_id", nullable = false)
    private Trajet trajet;

    private LocalDate datePanne;
    private String description;

    @Enumerated(EnumType.STRING)
    private UrgencePanne urgence;

    @OneToOne(mappedBy = "panne", cascade = CascadeType.ALL, orphanRemoval = true)
    private Entretien entretien;

}