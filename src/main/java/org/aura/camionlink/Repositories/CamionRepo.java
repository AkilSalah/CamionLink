package org.aura.camionlink.Repositories;

import org.aura.camionlink.DTO.CamionPanneDetails;
import org.aura.camionlink.Entities.Camion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CamionRepo extends JpaRepository<Camion,Long> {
    @Query("SELECT count(c) FROM Camion c")
    Long countCamion();
    @Query(value = """
    SELECT
    c.id AS camionId,
    c.marque,
    c.modele,
    c.etat AS etat,
    p.description AS descriptionPanne,
    COUNT(e.id) AS nombreEntretiens
    FROM
    public.camion c
    JOIN trajets t ON c.id = t.camion_id
    JOIN panne p ON t.id = p.trajet_id
    JOIN entretien e ON p.id = e.panne_id
    GROUP BY
    c.id, c.marque, c.modele, c.etat, p.description;
    """,nativeQuery = true)
    List<CamionPanneDetails> findCamionPanneDetails();
}
