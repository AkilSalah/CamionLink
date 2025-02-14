package org.aura.camionlink.Services.Interface;

import org.aura.camionlink.DTO.AuthenticationResponse;
import org.aura.camionlink.DTO.LoginRequest;
import org.aura.camionlink.DTO.RegisterConducteurRequest;

public interface AuthService {
AuthenticationResponse registerConducteur(RegisterConducteurRequest request);
AuthenticationResponse login(LoginRequest request);
void AddToken(String token);
Boolean GetTokens(String token);
} 
