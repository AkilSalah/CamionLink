package org.aura.camionlink.Mapper;

import org.aura.camionlink.DTO.CargaisonRequest;
import org.aura.camionlink.DTO.CargaisonResponse;
import org.aura.camionlink.Entities.Cargaison;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel =  "spring")
public interface CargaisonMapper {
    @Mapping(target = "trajet" , ignore = true)
    @Mapping(target = "id" , ignore = true)
    Cargaison toEntity (CargaisonRequest cargaisonRequest);
    CargaisonResponse toCargaisonResponse (Cargaison cargaison);

    @Mapping(target = "trajet" , ignore = true)
    @Mapping(target = "id" , ignore = true)
    void updateCargaisonFromRequset(CargaisonRequest cargaisonRequest 
    , @MappingTarget Cargaison cargaison );
}
