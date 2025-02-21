package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Panne;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PanneRepo extends JpaRepository<Panne,Long> {
}
