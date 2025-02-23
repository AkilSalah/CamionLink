package org.aura.camionlink.Services.Interface;

import org.aura.camionlink.DTO.EntretienRequest;
import org.aura.camionlink.DTO.EntretienResponse;
import org.aura.camionlink.Entities.Entretien;

import java.util.List;

public interface EntretienService {
    EntretienResponse createEntretien(EntretienRequest entretien);
    EntretienResponse getEntretien(long id);
    List<EntretienResponse> getAllEntretien();
    EntretienResponse updateEntretien(EntretienRequest request,long id);
    void deleteEntretien(long id);
}
