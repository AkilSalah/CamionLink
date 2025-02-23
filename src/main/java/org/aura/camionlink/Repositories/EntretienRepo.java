package org.aura.camionlink.Repositories;


import org.aura.camionlink.Entities.Entretien;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EntretienRepo extends JpaRepository<Entretien,Long> {
}
