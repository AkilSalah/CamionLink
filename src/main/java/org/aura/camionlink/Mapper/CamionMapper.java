package org.aura.camionlink.Mapper;

import org.aura.camionlink.DTO.CamionRequest;
import org.aura.camionlink.DTO.CamionResponse;
import org.aura.camionlink.Entities.Camion;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CamionMapper {
    @Mapping(target = "entretiens" , ignore = true)
    @Mapping(target = "trajets" , ignore = true)
    @Mapping(target = "id", ignore = true)
    Camion toEntity(CamionRequest camionRequest);

    CamionResponse tCamionResponse(Camion camion);

    @Mapping(target = "entretiens" , ignore = true)
    @Mapping(target = "trajets" , ignore = true)
    @Mapping(target = "id", ignore = true)
    void updateCamionFromRequest(CamionRequest camionRequest , @MappingTarget Camion camion);


}
