package org.aura.camionlink.Services.Interface;

import org.aura.camionlink.DTO.UtilisateurDTO;

public interface AuthService {
    UtilisateurDTO register(UtilisateurDTO userDTO);
    UtilisateurDTO login(UtilisateurDTO userDTO);
} 
