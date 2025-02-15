package org.aura.camionlink.Mapper;
import org.aura.camionlink.DTO.ConducteurResponse;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import org.aura.camionlink.Entities.Conducteur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
@Mapper(componentModel = "spring")
public interface ConducteurMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trajets", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "numeroPermis", target = "numeroPermis")
    Conducteur toEntity(RegisterConducteurRequest request);

    ConducteurResponse toResponse(Conducteur conducteur);
    
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "password", ignore = true) 
    void updateConducteurFromRequest(RegisterConducteurRequest request, @MappingTarget Conducteur conducteur);

}