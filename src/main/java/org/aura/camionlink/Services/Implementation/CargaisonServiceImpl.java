package org.aura.camionlink.Services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.aura.camionlink.DTO.CargaisonRequest;
import org.aura.camionlink.DTO.CargaisonResponse;
import org.aura.camionlink.Entities.Cargaison;
import org.aura.camionlink.Entities.Enums.StatutCargaison;
import org.aura.camionlink.Mapper.CargaisonMapper;
import org.aura.camionlink.Repositories.CargaisonRepo;
import org.aura.camionlink.Services.Interface.CargaisonService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class CargaisonServiceImpl implements CargaisonService{
    private final CargaisonRepo cargaisonRepository;
    private final CargaisonMapper cargaisonMapper;

    @Override
    public CargaisonResponse createCargaison(CargaisonRequest request) {
        Cargaison cargaison = cargaisonMapper.toEntity(request);
        cargaison.setCargaisonStatut(StatutCargaison.EN_COURS);
        Cargaison savedCargaison = cargaisonRepository.save(cargaison);
        return cargaisonMapper.toCargaisonResponse(savedCargaison);
    }

    @Override
    public CargaisonResponse getCargaisonById(Long id) {
        Cargaison cargaison = cargaisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargaison not found"));
        return cargaisonMapper.toCargaisonResponse(cargaison);
    }

    @Override
    public List<CargaisonResponse> getAllCargaisons() {
        return cargaisonRepository.findAll()
                .stream()
                .map(cargaisonMapper::toCargaisonResponse)
                .collect(Collectors.toList());
    }

    @Override
    public CargaisonResponse updateCargaison(Long id, CargaisonRequest request) {
        Cargaison cargaison = cargaisonRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Cargaison not found"));
        cargaisonMapper.updateCargaisonFromRequset(request, cargaison);
        Cargaison updatedCargaison = cargaisonRepository.save(cargaison);
        return cargaisonMapper.toCargaisonResponse(updatedCargaison);
    }

    @Override
    public void deleteCargaison(Long id) {
        cargaisonRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Cette cargaison n'exist pas")
        );
        cargaisonRepository.deleteById(id);  
      }

    @Override
    public Long getCargaisonCount() {
        return cargaisonRepository.count();
    }
}


