package org.aura.camionlink.Services.Interface;

import java.util.List;

import org.aura.camionlink.DTO.CargaisonRequest;
import org.aura.camionlink.DTO.CargaisonResponse;

public interface CargaisonService {
    CargaisonResponse createCargaison(CargaisonRequest request);
    CargaisonResponse getCargaisonById(Long id);
    List<CargaisonResponse> getAllCargaisons();
    CargaisonResponse updateCargaison(Long id, CargaisonRequest request);
    void deleteCargaison(Long id);
}
