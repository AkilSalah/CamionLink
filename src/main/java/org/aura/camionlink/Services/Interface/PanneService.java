package org.aura.camionlink.Services.Interface;

import org.aura.camionlink.DTO.PanneRequest;
import org.aura.camionlink.DTO.PanneResponse;

import java.util.List;

public interface PanneService {
    PanneResponse createPanne(PanneRequest panneRequest,long conducteurId);
    void deletePanne(Long panneId,long conducteurId);
    List<PanneResponse> getAllPanne();
    List<PanneResponse> getTrajetPanne(long trajetId,long conducteurId);
    PanneResponse getPanneById(Long panneId);
}
