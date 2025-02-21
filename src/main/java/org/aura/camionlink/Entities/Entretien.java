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
    private EtatEntretien etatEntretien;
    private double cout;
    @ManyToOne
    @JoinColumn(name = "camionId")
    private Camion camion ;
    @OneToOne
    @JoinColumn(name = "panne_id")
    private Panne panne;
}
