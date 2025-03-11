package org.aura.camionlink.Services.Interface;

import java.util.List;

import org.aura.camionlink.DTO.CamionRequest;
import org.aura.camionlink.DTO.CamionResponse;

public interface CamionService {
    CamionResponse addCamion(CamionRequest camionRequest);
    CamionResponse updateCamion(Long id, CamionRequest camionRequest);
    void deleteCamion(Long id);
    CamionResponse getCamionById(Long id);
    List<CamionResponse> getAllCamions();
    Long getCamionCount();
}
