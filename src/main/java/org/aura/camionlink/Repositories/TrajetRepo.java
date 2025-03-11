package org.aura.camionlink.Repositories;

import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TrajetRepo extends JpaRepository<Trajet , Long>{
    List<Trajet> findByConducteur(Conducteur conducteur);
    long count();
    long countByConducteur(Conducteur conducteur);
    
}
