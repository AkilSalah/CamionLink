package org.aura.camionlink.Repositories;

import java.util.List;

import org.aura.camionlink.Entities.Depense;
import org.aura.camionlink.Entities.Trajet;
import org.aura.camionlink.Entities.Enums.DepenseStatut;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DepenseRepo extends JpaRepository<Depense,Long> {
    @Modifying
    @Query("UPDATE Depense d SET d.statut = :statut WHERE d.id = :id") 
    void updateStatut(@Param("id") Long id, @Param("statut") DepenseStatut statut);

    List<Depense> findByTrajet(Trajet trajet);
}
