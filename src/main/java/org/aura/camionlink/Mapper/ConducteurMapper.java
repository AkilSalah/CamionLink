package org.aura.camionlink.Mapper;
import org.aura.camionlink.DTO.RegisterConducteurRequest;
import org.aura.camionlink.Entities.Conducteur;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
@Mapper(componentModel = "spring")
public interface ConducteurMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "trajets", ignore = true)
    @Mapping(target = "password", ignore = true)
    @Mapping(source = "numeroPermis", target = "numeroPermis")
    Conducteur toEntity(RegisterConducteurRequest request);
}