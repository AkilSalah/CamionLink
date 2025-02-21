package org.aura.camionlink.Mapper;

import org.aura.camionlink.DTO.PanneRequest;
import org.aura.camionlink.Entities.Panne;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PanneMapper {

    Panne toEntity(PanneRequest panneRequest);
    PanneRequest toResponse(Panne panne);

    void updatePanne(PanneRequest panneRequest, @MappingTarget Panne panne);
}
