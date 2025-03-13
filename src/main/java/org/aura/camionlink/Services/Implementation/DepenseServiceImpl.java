package org.aura.camionlink.Services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.aura.camionlink.DTO.DepenseRequest;
import org.aura.camionlink.DTO.DepenseResponse;
import org.aura.camionlink.Entities.Depense;
import org.aura.camionlink.Entities.Trajet;
import org.aura.camionlink.Entities.Enums.DepenseStatut;
import org.aura.camionlink.Exceptions.DepenseException;
import org.aura.camionlink.Exceptions.TrajetException;
import org.aura.camionlink.Exceptions.UnauthorizedException;
import org.aura.camionlink.Mapper.DepenseMapper;
import org.aura.camionlink.Repositories.DepenseRepo;
import org.aura.camionlink.Repositories.TrajetRepo;
import org.aura.camionlink.Services.Interface.DepenseService;
import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class DepenseServiceImpl implements DepenseService {

    private final DepenseRepo depenseRepository;
    private final TrajetRepo trajetRepository;
    private final DepenseMapper depenseMapper;

    @Override
    public List<DepenseResponse> getDepenses() {
        return depenseRepository.findAll().stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DepenseResponse> getDepenseByTrajet(long trajetId) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new TrajetException(trajetId));
        return depenseRepository.findByTrajet(trajet).stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepenseResponse createDepense(DepenseRequest request, long conducteurId) {
        Trajet trajet = trajetRepository.findById(request.trajetId())
                .orElseThrow(() -> new TrajetException(request.trajetId()));

        if (trajet.getConducteur() == null || trajet.getConducteur().getId() != conducteurId) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à ajouter une dépense pour ce trajet.");
        }

        Depense depense = depenseMapper.toEntity(request);
        depense.setTrajet(trajet);
        depense.setStatut(DepenseStatut.EN_ATTENTE);

        return depenseMapper.toResponse(depenseRepository.save(depense));
    }

    @Override
    public DepenseResponse updateDepense(DepenseRequest request, long depenseId, long conducteurId) {
        Depense depense = depenseRepository.findById(depenseId)
                .orElseThrow(() -> new DepenseException(depenseId));

        if (depense.getStatut() == DepenseStatut.VALIDEE || depense.getStatut() == DepenseStatut.REFUSEE) {
            throw new IllegalStateException("Impossible de modifier une dépense validée ou refusée.");
        }

        Trajet trajet = depense.getTrajet();
        if (trajet.getConducteur() == null || trajet.getConducteur().getId() != conducteurId) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier cette dépense.");
        }

        depenseMapper.updateDepenseFromRequest(request, depense);
        return depenseMapper.toResponse(depenseRepository.save(depense));
    }

    @Override
    public void deleteDepense(long id, long conducteurId) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new DepenseException(id));

        Trajet trajet = depense.getTrajet();
        if (trajet.getConducteur() == null || trajet.getConducteur().getId() != conducteurId) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à supprimer cette dépense.");
        }

        if (depense.getStatut() == DepenseStatut.VALIDEE || depense.getStatut() == DepenseStatut.REFUSEE) {
            throw new IllegalStateException("Impossible de supprimer une dépense validée ou refusée.");
        }

        depenseRepository.delete(depense);
    }

    @Override
    public DepenseResponse validateDepense(long id, DepenseStatut statut) {
        if (statut != DepenseStatut.VALIDEE && statut != DepenseStatut.REFUSEE) {
            throw new IllegalArgumentException("Statut invalide. Doit être VALIDEE ou REFUSEE.");
        }

        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new DepenseException(id));

        depense.setStatut(statut);
        return depenseMapper.toResponse(depenseRepository.save(depense));
    }
}
