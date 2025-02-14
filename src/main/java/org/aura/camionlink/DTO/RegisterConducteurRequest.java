package org.aura.camionlink.DTO;

import org.aura.camionlink.Entities.Enums.ConducteurStatut;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record RegisterConducteurRequest(
    @NotBlank(message = "Le nom est obligatoire", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    String nom,

    @NotBlank(message = "Le prénom est obligatoire", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    String prenom,

    @NotBlank(message = "L'email est obligatoire", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    @Email(message = "Format d'email invalide", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    String email,

    @NotBlank(message = "Le mot de passe est obligatoire", groups = ValidationGroups.OnCreate.class)
    String password,

    @NotNull(message = "Le contact est obligatoire", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    @Min(value = 0, message = "Le contact doit être positif", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    Integer contact,

    @JsonProperty("numeroPermis")
    String numeroPermis,

    @NotNull(message = "La disponibilité est obligatoire", groups = {ValidationGroups.OnCreate.class, ValidationGroups.OnUpdate.class})
    ConducteurStatut disponibilite
) {}