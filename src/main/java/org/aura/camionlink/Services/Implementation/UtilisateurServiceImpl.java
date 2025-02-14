package org.aura.camionlink.Services.Implementation;

import java.util.List;
import java.util.stream.Collectors;

import org.aura.camionlink.DTO.ConducteurResponse;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Exceptions.ConducteurException;
import org.aura.camionlink.Mapper.ConducteurMapper;
import org.aura.camionlink.Repositories.ConducteurRepo;
import org.aura.camionlink.Services.Interface.UtilisateurService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional

public class UtilisateurServiceImpl implements UtilisateurService {

    private final ConducteurRepo conducteurRepository;
    private final ConducteurMapper conducteurMapper;
    private final PasswordEncoder passwordEncoder;


    @Override
    public List<ConducteurResponse> getConducteurs() {
        List<Conducteur> conducteurs = conducteurRepository.findAll();
        List<ConducteurResponse> result = conducteurs.stream().map(conducteurMapper::toResponse)
        .collect(Collectors.toList());
        return result;
    }

    @Override
    public ConducteurResponse updateConducteur(RegisterConducteurRequest request, Long id) {
        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new ConducteurException(id));

        conducteurMapper.updateConducteurFromRequest(request, conducteur);
        if (request.password() != null && !request.password().isEmpty()) {
            conducteur.setPassword(passwordEncoder.encode(request.password()));
        }
        Conducteur updatedConducteur = conducteurRepository.save(conducteur);
        return conducteurMapper.toResponse(updatedConducteur);
    }

    @Override
    public void deleteConducteur(Long id) {
        Conducteur conducteur = conducteurRepository.findById(id)
                .orElseThrow(() -> new ConducteurException(id));
        conducteurRepository.delete(conducteur);
    }
}
