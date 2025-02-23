package org.aura.camionlink.Services.Implementation;


import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.aura.camionlink.DTO.EntretienRequest;
import org.aura.camionlink.DTO.EntretienResponse;
import org.aura.camionlink.Entities.Entretien;
import org.aura.camionlink.Entities.Panne;
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
        return entretienMapper.toResponse(entretienRepo.save(entretien));
    }

    @Override
    public EntretienResponse getEntretien(long id) {
        Entretien entretien = entretienRepo.findById(id).orElseThrow(
                () -> new PanneException(id)
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
                () -> new PanneException(id)
        );
        entretienMapper.updateEntretien(request,entretien);
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
