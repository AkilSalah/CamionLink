package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CamionRepo extends JpaRepository<Camion,Long> {
}
