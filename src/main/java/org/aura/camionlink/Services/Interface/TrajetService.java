package org.aura.camionlink.Services.Interface;

import java.util.List;

import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;

public interface TrajetService {
    TrajetResponse createTrajet(TrajetRequest request);
    TrajetResponse getTrajetById(Long id);
    List<TrajetResponse> getAllTrajets();
    TrajetResponse updateTrajet(Long id, TrajetRequest request);
    void deleteTrajet(Long id);
}
