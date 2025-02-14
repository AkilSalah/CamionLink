package org.aura.camionlink.Services.Interface;

import org.aura.camionlink.DTO.ConducteurResponse;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import java.util.List;

public interface UtilisateurService {
    List<ConducteurResponse>getConducteurs();
    ConducteurResponse updateConducteur(RegisterConducteurRequest request , Long id);
    void deleteConducteur(Long id);
}
