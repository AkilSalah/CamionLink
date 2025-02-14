package org.aura.camionlink.Services.Interface;

import java.util.List;

import org.aura.camionlink.DTO.DepenseRequest;
import org.aura.camionlink.DTO.DepenseResponse;

public interface DepenseService {
    List<DepenseResponse> getDepenses();
    List<DepenseResponse> getDepenseByTrajet(long trajetId);
    DepenseResponse createDepense(DepenseRequest request);
    DepenseResponse updateDepense(DepenseRequest request);
    void deleteDepense(long id);   
}
