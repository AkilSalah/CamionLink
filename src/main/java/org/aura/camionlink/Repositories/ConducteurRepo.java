package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ConducteurRepo extends JpaRepository <Conducteur, Long> {
    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE type(u) = Conducteur ")
    Long countConducteur();
}
