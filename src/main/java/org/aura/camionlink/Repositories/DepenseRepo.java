package org.aura.camionlink.Repositories;

import java.util.List;

import org.aura.camionlink.Entities.Depense;
import org.aura.camionlink.Entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DepenseRepo extends JpaRepository<Depense,Long> {
    List<Depense> findByTrajet(Trajet trajet);
}
