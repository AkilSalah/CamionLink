package org.aura.camionlink.Services.Implementation;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.aura.camionlink.DTO.PanneRequest;
import org.aura.camionlink.DTO.PanneResponse;
import org.aura.camionlink.Entities.Panne;
import org.aura.camionlink.Entities.Trajet;
import org.aura.camionlink.Exceptions.PanneException;
import org.aura.camionlink.Exceptions.TrajetException;
import org.aura.camionlink.Exceptions.UnauthorizedException;
import org.aura.camionlink.Mapper.PanneMapper;
import org.aura.camionlink.Repositories.PanneRepo;
import org.aura.camionlink.Repositories.TrajetRepo;
import org.aura.camionlink.Services.Interface.PanneService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class PanneServiceImpl implements PanneService {
    private final PanneRepo panneRepo;
    private final PanneMapper panneMapper;
    private final TrajetRepo trajetRepo;

    @Override
    public PanneResponse createPanne(PanneRequest panneRequest,long conducteurId) {
        Trajet existingTrajet = trajetRepo.findById(panneRequest.trajetId()).orElseThrow(
                () -> new TrajetException(panneRequest.trajetId())
        );
        if (conducteurId != existingTrajet.getConducteur().getId()){
            throw new UnauthorizedException("Vous n'êtes pas autorisé à ajouter une panne pour ce trajet.");
        }
        Panne panne = panneMapper.toEntity(panneRequest);
        Panne savedPanne = panneRepo.save(panne);
        return panneMapper.toResponse(savedPanne);
    }

    @Override
    public void deletePanne(Long panneId,long conducteurId) {
        Panne panne = panneRepo.findById(panneId).orElseThrow(
                () -> new PanneException(panneId)
        );
        if (panne.getTrajet().getConducteur().getId() != conducteurId){
            throw new UnauthorizedException("Vous n'êtes pas autorisé à supprimer une panne pour ce trajet.");
        }
        panneRepo.delete(panne);
    }

    @Override
    public List<PanneResponse> getAllPanne() {
        List<Panne> panneList = panneRepo.findAll();
        return panneList.stream().map(panneMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public PanneResponse getPanneById(Long panneId) {
        Panne panne = panneRepo.findById(panneId).orElseThrow(
                () -> new PanneException(panneId)
        );
        return panneMapper.toResponse(panne);
    }
}
