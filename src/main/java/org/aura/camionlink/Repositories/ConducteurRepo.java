package org.aura.camionlink.Repositories;

import org.aura.camionlink.DTO.ConducteurResponse;
import org.aura.camionlink.Entities.Conducteur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ConducteurRepo extends JpaRepository <Conducteur, Long> {
    @Query("SELECT COUNT(u) FROM Utilisateur u WHERE type(u) = Conducteur ")
    Long countConducteur();

    @Query(value = """
    SELECT u.* ,count (t.id) As trajetNumber\s
    FROM public.utilisateur u join trajets t on t.conducteur_id = u.id\s
    GROUP by u.id
    order by trajetNumber
    LIMIT 4
    """, nativeQuery = true)
    List<Conducteur> getEliteConducteur ();
}
