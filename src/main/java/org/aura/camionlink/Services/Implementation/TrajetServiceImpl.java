package org.aura.camionlink.Services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.aura.camionlink.DTO.ConducteurTrajetStats;
import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;
import org.aura.camionlink.Entities.Camion;
import org.aura.camionlink.Entities.Cargaison;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Enums.CamionEtat;
import org.aura.camionlink.Entities.Enums.ConducteurStatut;
import org.aura.camionlink.Entities.Trajet;
import org.aura.camionlink.Entities.Enums.TrajetStatut;
import org.aura.camionlink.Exceptions.CamionException;
import org.aura.camionlink.Exceptions.CargaisonException;
import org.aura.camionlink.Exceptions.ConducteurException;
import org.aura.camionlink.Exceptions.TrajetException;
import org.aura.camionlink.Exceptions.UnauthorizedException;
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
        if(conducteur.getDisponibilite().equals(ConducteurStatut.EN_CONGE) ||
           conducteur.getDisponibilite().equals(ConducteurStatut.EN_MISSION) ){
            throw new IllegalStateException("Le conducteur n'est pas disponible pour un nouveau trajet.");
        }
        if(camion.getEtat().equals(CamionEtat.EN_MAINTENANCE) ||
            camion.getEtat().equals(CamionEtat.HORS_SERVICE)){
            throw new IllegalStateException("Le camion sélectionné n'est pas en état de rouler.");
        }
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

    @Override
    public List<TrajetResponse> getConducteurTrajets(Long id){
        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new ConducteurException(id));

        List<Trajet> results = trajetRepository.findByConducteur(conducteur);

        boolean hasUnauthorizedTrajet = results.stream()
                .anyMatch(trajet -> !trajet.getConducteur().getId().equals(id));

        if (hasUnauthorizedTrajet) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à récupérer ces trajets.");
        }

        return results.stream()
                .map(trajetMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public TrajetResponse updateTrajetStatus(Long conducteurId, Long trajetId, TrajetStatut statut) {
        Trajet trajet = trajetRepository.findById(trajetId).orElseThrow(
                () -> new TrajetException(trajetId)
        );

        if (!trajet.getConducteur().getId().equals(conducteurId)) {
            throw new UnauthorizedException("Vous n'êtes pas autorisé à modifier ce trajet.");
        }

        trajet.setStatut(statut);

        if (statut == TrajetStatut.EN_COURS || statut == TrajetStatut.EN_RETARD) {
            trajet.getConducteur().setDisponibilite(ConducteurStatut.EN_MISSION);
            trajet.getCamion().setEtat(CamionEtat.EN_MISSION);
        } else if (statut == TrajetStatut.TERMINE) {
            trajet.getConducteur().setDisponibilite(ConducteurStatut.DISPONIBLE);
            trajet.getCamion().setEtat(CamionEtat.DISPONIBLE);
        }

        conducteurRepository.save(trajet.getConducteur());
        camionRepository.save(trajet.getCamion());
        Trajet updatedTrajet = trajetRepository.save(trajet);

        return trajetMapper.toResponse(updatedTrajet);
    }

    @Override
    public Long getTrajetCount() {
        return trajetRepository.count();
    }

    @Override
    public List<ConducteurTrajetStats> getConducteurTrajetStats() {
        return trajetRepository.findConducteurTrajetStats();
    }
}
