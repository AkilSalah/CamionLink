package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TrajetRepo extends JpaRepository<Trajet , Long>{
}
