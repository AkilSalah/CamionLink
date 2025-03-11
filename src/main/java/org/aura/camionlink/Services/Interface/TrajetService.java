package org.aura.camionlink.Services.Interface;

import java.util.List;

import org.aura.camionlink.DTO.ConducteurTrajetStats;
import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;
import org.aura.camionlink.Entities.Enums.TrajetStatut;

public interface TrajetService {
    TrajetResponse createTrajet(TrajetRequest request);
    TrajetResponse getTrajetById(Long id);
    List<TrajetResponse> getAllTrajets();
    TrajetResponse updateTrajet(Long id, TrajetRequest request);
    void deleteTrajet(Long id);
    List<TrajetResponse> getConducteurTrajets(Long id);
    TrajetResponse updateTrajetStatus(Long conducteurId, Long trajetId, TrajetStatut statut);
    Long getTrajetCount();
    List<ConducteurTrajetStats> getConducteurTrajetStats();
}
