package org.aura.camionlink.Entities;

import jakarta.persistence.DiscriminatorValue;

@DiscriminatorValue("ADMIN")
public class Admin extends Utilisateur {
}
