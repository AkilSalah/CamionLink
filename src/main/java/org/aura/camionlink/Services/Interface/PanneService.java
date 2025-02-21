package org.aura.camionlink.Services.Interface;

import org.aura.camionlink.DTO.PanneRequest;
import org.aura.camionlink.DTO.PanneResponse;

import java.util.List;

public interface PanneService {
    PanneResponse createPanne(PanneRequest panneRequest);
    PanneResponse deletePanne(Long panneId);
    List<PanneResponse> getAllPanne();
    PanneResponse getPanneById(Long panneId);

}
