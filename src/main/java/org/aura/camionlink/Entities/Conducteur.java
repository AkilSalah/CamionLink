package org.aura.camionlink.Entities;

import java.util.ArrayList;
import java.util.List;

import org.aura.camionlink.Entities.Enums.ConducteurStatut;

import jakarta.persistence.CascadeType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.OneToMany;
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

    @OneToMany(mappedBy = "conducteur", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Trajet> trajets = new ArrayList<>();
}