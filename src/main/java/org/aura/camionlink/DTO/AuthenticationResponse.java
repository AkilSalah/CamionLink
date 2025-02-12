package org.aura.camionlink.DTO;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AuthenticationResponse {
    private String token;
    private String type;  
    private Long userId;
}