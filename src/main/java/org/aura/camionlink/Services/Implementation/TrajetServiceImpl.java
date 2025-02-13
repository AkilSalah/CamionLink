package org.aura.camionlink.Services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;
import org.aura.camionlink.Entities.Camion;
import org.aura.camionlink.Entities.Cargaison;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Trajet;
import org.aura.camionlink.Exceptions.CamionException;
import org.aura.camionlink.Exceptions.CargaisonException;
import org.aura.camionlink.Exceptions.ConducteurException;
import org.aura.camionlink.Exceptions.TrajetException;
import org.aura.camionlink.Mapper.TrajetMapper;
import org.aura.camionlink.Repositories.CamionRepo;
import org.aura.camionlink.Repositories.CargaisonRepo;
import org.aura.camionlink.Repositories.ConducteurRepo;
import org.aura.camionlink.Repositories.TrajetRepo;
import org.aura.camionlink.Services.Interface.TrajetService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class TrajetServiceImpl implements TrajetService {

    private final TrajetRepo trajetRepository;
    private final ConducteurRepo conducteurRepository;
    private final CamionRepo camionRepository;
    private final CargaisonRepo cargaisonRepository;
    private final TrajetMapper trajetMapper;

    @Override
    public TrajetResponse createTrajet(TrajetRequest request) {

        Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                .orElseThrow(() -> new ConducteurException (request.conducteurId()));
        Camion camion = camionRepository.findById(request.camionId())
                .orElseThrow(() -> new CamionException(request.camionId()));
        Cargaison cargaison = cargaisonRepository.findById(request.cargaisonId())
                .orElseThrow(() -> new CargaisonException(request.cargaisonId()));

        Trajet trajet = trajetMapper.toEntity(request);
        trajet.setConducteur(conducteur);
        trajet.setCamion(camion);
        trajet.setCargaison(cargaison);
        Trajet savedTrajet = trajetRepository.save(trajet);
        return trajetMapper.toResponse(savedTrajet);
    }

    @Override
    public TrajetResponse getTrajetById(Long id) {
        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new TrajetException(id));
        return trajetMapper.toResponse(trajet);
    }

    @Override
    public List<TrajetResponse> getAllTrajets() {
        return trajetRepository.findAll()
                .stream()
                .map(trajetMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TrajetResponse updateTrajet(Long id, TrajetRequest request) {
        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new TrajetException(id));

        trajetMapper.updateTrajetFromRequest(request, trajet);

        if (request.conducteurId() != null) {
            Conducteur conducteur = conducteurRepository.findById(request.conducteurId())
                    .orElseThrow(() -> new ConducteurException( request.conducteurId()));
            trajet.setConducteur(conducteur);
        }
        if (request.camionId() != null) {
            Camion camion = camionRepository.findById(request.camionId())
                    .orElseThrow(() -> new CamionException(request.camionId()));
            trajet.setCamion(camion);
        }
        if (request.cargaisonId() != null) {
            Cargaison cargaison = cargaisonRepository.findById(request.cargaisonId())
                    .orElseThrow(() -> new CargaisonException(request.cargaisonId()));
            trajet.setCargaison(cargaison);
        }

        Trajet updatedTrajet = trajetRepository.save(trajet);
        return trajetMapper.toResponse(updatedTrajet);
    }

    @Override
    public void deleteTrajet(Long id) {
        trajetRepository.findById(id).orElseThrow(() -> new TrajetException(id));
        trajetRepository.deleteById(id);
    }
}
