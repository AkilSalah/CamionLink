package org.aura.camionlink.Entities;

import org.aura.camionlink.Entities.Enums.ConducteurStatut;

import jakarta.persistence.DiscriminatorValue;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@DiscriminatorValue("CONDUCTEUR")
@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
public class Conducteur extends Utilisateur {
    private String numeroPermis;
    private ConducteurStatut disponibilite;
}
