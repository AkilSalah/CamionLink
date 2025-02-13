package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ConducteurRepo extends JpaRepository <Conducteur, Long> {
}
