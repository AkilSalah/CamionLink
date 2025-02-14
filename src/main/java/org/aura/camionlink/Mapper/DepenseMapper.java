package org.aura.camionlink.Mapper;

import org.aura.camionlink.DTO.DepenseRequest;
import org.aura.camionlink.DTO.DepenseResponse;
import org.aura.camionlink.Entities.Depense;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DepenseMapper {
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trajet",ignore = true)
    Depense toEntity(DepenseRequest request);
    
    DepenseResponse toResponse(Depense depense);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trajet",ignore = true)
    void updateDepenseFromRequest(DepenseRequest request , @MappingTarget Depense ddepense);

}
