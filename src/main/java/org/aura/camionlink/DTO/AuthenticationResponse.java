package org.aura.camionlink.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private String type;  
    private Long userId;
    private String nom;
    private String prenom;
    private String email;
}