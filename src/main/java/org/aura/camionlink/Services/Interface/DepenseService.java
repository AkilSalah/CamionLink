package org.aura.camionlink.Services.Interface;

import java.util.List;

import org.aura.camionlink.DTO.DepenseRequest;
import org.aura.camionlink.DTO.DepenseResponse;
import org.aura.camionlink.Entities.Enums.DepenseStatut;

public interface DepenseService {
    List<DepenseResponse> getDepenses();
    List<DepenseResponse> getDepenseByTrajet(long trajetId);
    DepenseResponse createDepense(DepenseRequest request);
    DepenseResponse updateDepense(DepenseRequest request , long id);
    void deleteDepense(long id);   
    DepenseResponse validateDepense(long id , DepenseStatut statut);
}
