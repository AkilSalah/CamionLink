package org.aura.camionlink.Mapper;

import org.aura.camionlink.DTO.EntretienRequest;
import org.aura.camionlink.DTO.EntretienResponse;
import org.aura.camionlink.Entities.Entretien;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EntretienMapper {
    @Mapping(target = "panne.id",source ="panneId" )
    Entretien toEntity(EntretienRequest request);
    @Mapping(target = "panne",source ="panne" )
    EntretienResponse toResponse(Entretien entretien);
    void updateEntretien(EntretienRequest request , @MappingTarget Entretien entretien);
}
