package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Cargaison;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargaisonRepo extends JpaRepository<Cargaison , Long> {
    long count();
}
