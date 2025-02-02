package org.aura.camionlink.Entities;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;


@Entity
@DiscriminatorValue("ADMIN")
public class Admin extends Utilisateur {
}
