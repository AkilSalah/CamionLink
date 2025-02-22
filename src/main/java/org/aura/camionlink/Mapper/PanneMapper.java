package org.aura.camionlink.Mapper;

import org.aura.camionlink.DTO.PanneRequest;
import org.aura.camionlink.DTO.PanneResponse;
import org.aura.camionlink.Entities.Panne;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PanneMapper {

    @Mapping(source = "trajetId" , target = "trajet.id")
    Panne toEntity(PanneRequest panneRequest);
    @Mapping(source = "trajet" , target = "trajet")
    PanneResponse toResponse(Panne panne);

    void updatePanne(PanneRequest panneRequest, @MappingTarget Panne panne);
}
