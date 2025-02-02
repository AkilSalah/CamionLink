package org.aura.camionlink.Repositories;

import java.util.Optional;

import org.aura.camionlink.Entities.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UtilisateurRepo extends JpaRepository<Utilisateur,Long> {
    Optional<Utilisateur> findByName(String nom);
    boolean existsByName(String nom);    
} 
    
