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
    @NotBlank(message = "Le nom est obligatoire")
    String nom,

    @NotBlank(message = "Le prénom est obligatoire")
    String prenom,

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format d'email invalide")
    String email,

    @NotBlank(message = "Le mot de passe est obligatoire")
    String password,

    @NotNull(message = "Le contact est obligatoire")
    @Min(value = 0, message = "Le contact doit être positif")
    Integer contact,

    @JsonProperty("numeroPermis")
    String numeroPermis,

    @NotNull(message = "La disponibilité est obligatoire")
    ConducteurStatut disponibilite
) {
}
