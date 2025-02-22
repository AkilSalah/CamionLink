package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Panne;
import org.aura.camionlink.Entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PanneRepo extends JpaRepository<Panne,Long> {
    List<Panne> findByTrajet(Trajet trajet);
}
