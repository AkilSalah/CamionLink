package org.aura.camionlink.Repositories;

import org.aura.camionlink.DTO.ConducteurTrajetStats;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Trajet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;


@Repository
public interface TrajetRepo extends JpaRepository<Trajet , Long>{
    List<Trajet> findByConducteur(Conducteur conducteur);
    long count();
    @Query(value = """
        SELECT 
            u.nom AS nom,
            u.prenom AS prenom,
            COUNT(t.id) AS nombreTrajets
        FROM 
            utilisateur u
        LEFT JOIN 
            trajets t ON t.conducteur_id = u.id
        GROUP BY 
            u.id, u.nom, u.prenom
        """, nativeQuery = true)
    List<ConducteurTrajetStats> findConducteurTrajetStats();
}
