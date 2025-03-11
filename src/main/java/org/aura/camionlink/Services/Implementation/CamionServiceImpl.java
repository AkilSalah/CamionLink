package org.aura.camionlink.Services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.aura.camionlink.DTO.CamionRequest;
import org.aura.camionlink.DTO.CamionResponse;
import org.aura.camionlink.Entities.Camion;
import org.aura.camionlink.Mapper.CamionMapper;
import org.aura.camionlink.Repositories.CamionRepo;
import org.aura.camionlink.Services.Interface.CamionService;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@Transactional
@RequiredArgsConstructor
public class CamionServiceImpl implements CamionService {
    private final CamionRepo camionRepository;
    private final CamionMapper camionMapper;

    @Override
    public CamionResponse addCamion(CamionRequest camionRequest){
        Camion camion = camionMapper.toEntity(camionRequest);
        Camion saveCamion = camionRepository.save(camion) ;
        return camionMapper.tCamionResponse(saveCamion);
    }

    @Override
    public Long getCamionCount() {
        return camionRepository.countCamion();
    }

    @Override
    public CamionResponse updateCamion(Long id , CamionRequest camionRequest){
        Camion existingCamion = camionRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Ce camion n'exist pas")
        );
        camionMapper.updateCamionFromRequest(camionRequest , existingCamion);
        Camion updatedCamion = camionRepository.save(existingCamion);
        return camionMapper.tCamionResponse(updatedCamion);
    }

    @Override 
    public void deleteCamion (Long id) {
        camionRepository.findById(id).orElseThrow(
            () -> new RuntimeException("Ce camion n'exist pas")
        );
        camionRepository.deleteById(id);
    }

      @Override
    public CamionResponse getCamionById(Long id) {
        Camion camion = camionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Camion not found"));
        return camionMapper.tCamionResponse(camion);
    }

    @Override
    public List<CamionResponse> getAllCamions() {
        return camionRepository.findAll().stream()
                .map(camionMapper::tCamionResponse)
                .collect(Collectors.toList());
    }

}
