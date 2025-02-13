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

        Conducteur conducteur = conducteurRepository.findById(request.conducteur_id())
                .orElseThrow(() -> new ConducteurException (request.conducteur_id()));
        Camion camion = camionRepository.findById(request.camion_id())
                .orElseThrow(() -> new CamionException(request.camion_id()));
        Cargaison cargaison = cargaisonRepository.findById(request.cargaison_id())
                .orElseThrow(() -> new CargaisonException(request.cargaison_id()));

        Trajet trajet = trajetMapper.toEntity(request);
        trajet.setConducteur(conducteur);
        trajet.setCamion(camion);
        trajet.setCargaison(cargaison);

        Trajet savedTrajet = trajetRepository.save(trajet);
        return trajetMapper.toTrajetResponse(savedTrajet);
    }

    @Override
    public TrajetResponse getTrajetById(Long id) {
        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new TrajetException(id));
        return trajetMapper.toTrajetResponse(trajet);
    }

    @Override
    public List<TrajetResponse> getAllTrajets() {
        return trajetRepository.findAll()
                .stream()
                .map(trajetMapper::toTrajetResponse)
                .collect(Collectors.toList());
    }

    @Override
    public TrajetResponse updateTrajet(Long id, TrajetRequest request) {
        Trajet trajet = trajetRepository.findById(id)
                .orElseThrow(() -> new TrajetException(id));

        trajetMapper.updateTrajetFromRequest(request, trajet);

        if (request.conducteur_id() != null) {
            Conducteur conducteur = conducteurRepository.findById(request.conducteur_id())
                    .orElseThrow(() -> new ConducteurException( request.conducteur_id()));
            trajet.setConducteur(conducteur);
        }
        if (request.camion_id() != null) {
            Camion camion = camionRepository.findById(request.camion_id())
                    .orElseThrow(() -> new CamionException(request.camion_id()));
            trajet.setCamion(camion);
        }
        if (request.cargaison_id() != null) {
            Cargaison cargaison = cargaisonRepository.findById(request.cargaison_id())
                    .orElseThrow(() -> new CargaisonException(request.cargaison_id()));
            trajet.setCargaison(cargaison);
        }

        Trajet updatedTrajet = trajetRepository.save(trajet);
        return trajetMapper.toTrajetResponse(updatedTrajet);
    }

    @Override
    public void deleteTrajet(Long id) {
        trajetRepository.findById(id).orElseThrow(() -> new TrajetException(id));
        trajetRepository.deleteById(id);
    }
}
