package org.aura.camionlink.Mapper;

import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;
import org.aura.camionlink.Entities.Trajet;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrajetMapper {
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conducteur", ignore = true)
    @Mapping(target = "camion", ignore = true)
    @Mapping(target = "cargaison", ignore = true)
    @Mapping(target = "depenses", ignore = true)
    Trajet toEntity(TrajetRequest request);

    TrajetResponse toTrajetResponse(Trajet trajet);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "conducteur", ignore = true)
    @Mapping(target = "camion", ignore = true)
    @Mapping(target = "cargaison", ignore = true)
    @Mapping(target = "depenses", ignore = true)
    void updateTrajetFromRequest(TrajetRequest request, @MappingTarget Trajet trajet);}
