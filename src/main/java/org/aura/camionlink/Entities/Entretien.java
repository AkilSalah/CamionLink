package org.aura.camionlink.Entities;

import java.time.LocalDate;


import jakarta.persistence.*;
import lombok.Data;
import org.aura.camionlink.Entities.Enums.EtatEntretien;

@Entity
@Data
public class Entretien {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private LocalDate dateEntretien;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EtatEntretien etatEntretien;
    private double cout;
    @OneToOne
    @JoinColumn(name = "panne_id", unique = true, nullable = false)
    private Panne panne;
}
