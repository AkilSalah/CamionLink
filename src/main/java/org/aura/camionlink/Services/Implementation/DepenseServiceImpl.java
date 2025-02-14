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
        List<Depense> depenses = depenseRepository.findAll();
        return depenses.stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public List<DepenseResponse> getDepenseByTrajet(long trajetId) {
        Trajet trajet = trajetRepository.findById(trajetId)
                .orElseThrow(() -> new TrajetException(trajetId));
        List<Depense> depenses = depenseRepository.findByTrajet(trajet);
        return depenses.stream()
                .map(depenseMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public DepenseResponse createDepense(DepenseRequest request) {
        Trajet trajet = trajetRepository.findById(request.trajetId())
                .orElseThrow(() -> new TrajetException(request.trajetId()));

        Depense depense = depenseMapper.toEntity(request);
        depense.setTrajet(trajet);

        Depense savedDepense = depenseRepository.save(depense);
        return depenseMapper.toResponse(savedDepense);
    }

    @Override
    public DepenseResponse updateDepense(DepenseRequest request, long id) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new DepenseException(id));

        if (depense.getStatut() == DepenseStatut.VALIDEE || depense.getStatut() == DepenseStatut.REFUSEE) {
            throw new IllegalStateException("Impossible de modifier une dépense validée ou refusée.");
        }

        depenseMapper.updateDepenseFromRequest(request, depense);

        Depense updatedDepense = depenseRepository.save(depense);
        return depenseMapper.toResponse(updatedDepense);
    }

    @Override
    public void deleteDepense(long id) {
        Depense depense = depenseRepository.findById(id)
                .orElseThrow(() -> new DepenseException(id));

        if (depense.getStatut() == DepenseStatut.VALIDEE || depense.getStatut() == DepenseStatut.REFUSEE) {
            throw new IllegalStateException("Impossible de supprimer une dépense validée ou refusée.");
        }
        depenseRepository.deleteById(id);
    }

    @Override
    public DepenseResponse validateDepense(long id , DepenseStatut statut){

        if (statut != DepenseStatut.VALIDEE && statut != DepenseStatut.REFUSEE) {
            throw new IllegalArgumentException("Statut invalide. Doit être VALIDEE ou REFUSEE.");
        }
        depenseRepository.updateStatut(id, statut);
        Depense updatedDepense = depenseRepository.findById(id)
            .orElseThrow(() -> new DepenseException(id));
        return depenseMapper.toResponse(updatedDepense);
    }
}
