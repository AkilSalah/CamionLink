package org.aura.camionlink.Services.Interface;

import java.util.List;

import org.aura.camionlink.DTO.DepenseRequest;
import org.aura.camionlink.DTO.DepenseResponse;
import org.aura.camionlink.Entities.Enums.DepenseStatut;

public interface DepenseService {
    List<DepenseResponse> getDepenses();
    List<DepenseResponse> getDepenseByTrajet(long trajetId);
    DepenseResponse createDepense(DepenseRequest request,long conducteurId);
    DepenseResponse updateDepense(DepenseRequest request , long conducteurId , long depenseId);
    void deleteDepense(long depenseId , long conducteurId);   
    DepenseResponse validateDepense(long id , DepenseStatut statut);
}
