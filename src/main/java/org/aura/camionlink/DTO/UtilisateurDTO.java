package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.ConducteurStatut;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UtilisateurDTO {
    private Long id;
    private String nom;
    private String prenom;
    private String email;
    private String password;
    private int contact;
    private String userType; 
    private String numeroPermis; 
    private ConducteurStatut disponibilite; 
    private String token;

}
