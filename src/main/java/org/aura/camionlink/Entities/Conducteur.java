package org.aura.camionlink.Entities;


import java.util.List;

import org.aura.camionlink.Entities.Enums.ConducteurStatut;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.OneToMany;

import lombok.Getter;
import lombok.Setter;


@Entity
@DiscriminatorValue("CONDUCTEUR")
@Getter
@Setter
public class Conducteur extends Utilisateur {
    private String numeroPermis;
    @Enumerated(EnumType.STRING)
    private ConducteurStatut disponibilite;

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trajet> trajets;
}