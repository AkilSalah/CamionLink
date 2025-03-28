package org.aura.camionlink.Services.Implementation;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.aura.camionlink.DTO.EntretienRequest;
import org.aura.camionlink.DTO.EntretienResponse;
import org.aura.camionlink.Entities.Entretien;
import org.aura.camionlink.Entities.Enums.CamionEtat;
import org.aura.camionlink.Entities.Enums.EtatEntretien;
import org.aura.camionlink.Entities.Enums.UrgencePanne;
import org.aura.camionlink.Entities.Panne;
import org.aura.camionlink.Exceptions.EntretienException;
import org.aura.camionlink.Exceptions.PanneException;
import org.aura.camionlink.Mapper.EntretienMapper;
import org.aura.camionlink.Repositories.EntretienRepo;
import org.aura.camionlink.Repositories.PanneRepo;
import org.aura.camionlink.Services.Interface.EntretienService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
public class EntretienServiceImpl implements EntretienService {

    private final PanneRepo panneRepo;
    private final EntretienRepo entretienRepo;
    private final EntretienMapper entretienMapper;

    @Override
    public EntretienResponse createEntretien(EntretienRequest request) {
        Panne existingPanne = panneRepo.findById(request.panneId()).orElseThrow(
                () -> new PanneException(request.panneId())
        );
        Entretien entretien = entretienMapper.toEntity(request);
        entretien.setPanne(existingPanne);

        if(entretien.getEtatEntretien().equals(EtatEntretien.EN_ATTENTE)|| entretien.getEtatEntretien().equals(EtatEntretien.EN_COURS)){
                existingPanne.getTrajet().getCamion().setEtat(CamionEtat.EN_MAINTENANCE);
                panneRepo.save(existingPanne);
        }
        if(entretien.getEtatEntretien().equals(EtatEntretien.TERMINE)){
            if (existingPanne.getUrgence().equals(UrgencePanne.IMMEDIATE)) {
                existingPanne.getTrajet().getCamion().setEtat(CamionEtat.EN_MISSION);
                panneRepo.save(existingPanne);
            }else {
                existingPanne.getTrajet().getCamion().setEtat(CamionEtat.DISPONIBLE);
                panneRepo.save(existingPanne);
            }
        }
        return entretienMapper.toResponse(entretienRepo.save(entretien));
    }

    @Override
    public EntretienResponse getEntretien(long id) {
        Entretien entretien = entretienRepo.findById(id).orElseThrow(
                () -> new EntretienException(id)
        );
        return entretienMapper.toResponse(entretien);
    }

    @Override
    public List<EntretienResponse> getAllEntretien() {
        List<Entretien> entretiens = entretienRepo.findAll();
        return entretiens.stream().map(entretienMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public EntretienResponse updateEntretien(EntretienRequest request, long id) {
        Entretien entretien = entretienRepo.findById(id).orElseThrow(
                () -> new EntretienException(id)
        );

        EtatEntretien previousState = entretien.getEtatEntretien();

        entretienMapper.updateEntretien(request, entretien);

        if (!previousState.equals(entretien.getEtatEntretien())) {
            Panne panne = entretien.getPanne();

            if (entretien.getEtatEntretien().equals(EtatEntretien.EN_ATTENTE) ||
                    entretien.getEtatEntretien().equals(EtatEntretien.EN_COURS)) {
                panne.getTrajet().getCamion().setEtat(CamionEtat.EN_MAINTENANCE);
            } else if (entretien.getEtatEntretien().equals(EtatEntretien.TERMINE)) {
                if (panne.getUrgence().equals(UrgencePanne.IMMEDIATE)) {
                    panne.getTrajet().getCamion().setEtat(CamionEtat.EN_MISSION);
                } else {
                    panne.getTrajet().getCamion().setEtat(CamionEtat.DISPONIBLE);
                }
            }
            panneRepo.save(panne);
        }

        Entretien updatedEntretien = entretienRepo.save(entretien);
        return entretienMapper.toResponse(updatedEntretien);
    }

    @Override
    public void deleteEntretien(long id) {
        entretienRepo.findById(id).orElseThrow(
                () -> new PanneException(id)
        );
        entretienRepo.deleteById(id);
    }
}
