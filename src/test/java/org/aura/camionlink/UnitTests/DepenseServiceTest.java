package org.aura.camionlink.UnitTests;

import org.aura.camionlink.DTO.DepenseRequest;
import org.aura.camionlink.DTO.DepenseResponse;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Depense;
import org.aura.camionlink.Entities.Trajet;
import org.aura.camionlink.Entities.Enums.DepenseStatut;
import org.aura.camionlink.Exceptions.TrajetException;
import org.aura.camionlink.Exceptions.UnauthorizedException;
import org.aura.camionlink.Mapper.DepenseMapper;
import org.aura.camionlink.Repositories.DepenseRepo;
import org.aura.camionlink.Repositories.TrajetRepo;
import org.aura.camionlink.Services.Implementation.DepenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForInterfaceTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DepenseServiceTest {

    @Mock
    private DepenseRepo depenseRepository;

    @Mock
    private TrajetRepo trajetRepository;

    @Mock
    private DepenseMapper depenseMapper;

    @InjectMocks
    private DepenseServiceImpl depenseService;

    private Depense depense;
    private DepenseRequest depenseRequest;
    private DepenseResponse depenseResponse;
    private Trajet trajet;
    private Conducteur conducteur;

    @BeforeEach
    void setUp() {
        conducteur = new Conducteur();
        conducteur.setId(1L);

        trajet = new Trajet();
        trajet.setId(1L);
        trajet.setConducteur(conducteur);

        depense = new Depense();
        depense.setId(1L);
        depense.setTypeDepense("ESSENCE");
        depense.setMontant(50.0);
        depense.setDate(LocalDate.now());
        depense.setStatut(DepenseStatut.EN_ATTENTE);
        depense.setTrajet(trajet);

        depenseRequest = DepenseRequest.builder()
                .typeDepense("ESSENCE")
                .montant(50.0)
                .date(LocalDate.now())
                .trajetId(1L)
                .build();

        depenseResponse = DepenseResponse.builder()
                .id(1L)
                .typeDepense("ESSENCE")
                .montant(50.0)
                .date(LocalDate.now())
                .statut(DepenseStatut.EN_ATTENTE)
                .build();
    }

    @Test
    void getDepenses_ShouldReturnListOfDepenses() {
        List<Depense> depenses = List.of(depense);
        when(depenseRepository.findAll()).thenReturn(depenses);
        when(depenseMapper.toResponse(any(Depense.class))).thenReturn(depenseResponse);

        List<DepenseResponse> result = depenseService.getDepenses();

        assertThat(result).hasSize(1);
        verify(depenseRepository).findAll();
        verify(depenseMapper).toResponse(depense);
    }

    @Test
    void getDepenseByTrajet_ShouldReturnDepensesForTrajet() {
        List<Depense> depenses = List.of(depense);
        when(trajetRepository.findById(1L)).thenReturn(Optional.of(trajet));
        when(depenseRepository.findByTrajet(trajet)).thenReturn(depenses);
        when(depenseMapper.toResponse(any(Depense.class))).thenReturn(depenseResponse);

        List<DepenseResponse> result = depenseService.getDepenseByTrajet(1L);

        assertThat(result).hasSize(1);
        verify(trajetRepository).findById(1L);
        verify(depenseRepository).findByTrajet(trajet);
    }

    @Test
    void getDepenseByTrajet_ShouldThrowException_WhenTrajetNotFound() {
        when(trajetRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(TrajetException.class, () ->
                depenseService.getDepenseByTrajet(1L));
    }

    @Test
    void createDepense_ShouldCreateNewDepense() {
        when(trajetRepository.findById(1L)).thenReturn(Optional.of(trajet));
        when(depenseMapper.toEntity(depenseRequest)).thenReturn(depense);
        when(depenseRepository.save(any(Depense.class))).thenReturn(depense);
        when(depenseMapper.toResponse(any(Depense.class))).thenReturn(depenseResponse);

        DepenseResponse result = depenseService.createDepense(depenseRequest, 1L);

        assertThat(result).isNotNull();
        verify(depenseRepository).save(depense);
        verify(depenseMapper).toResponse(depense);
    }

    @Test
    void createDepense_ShouldThrowUnauthorizedException_WhenWrongConducteur() {
        when(trajetRepository.findById(1L)).thenReturn(Optional.of(trajet));

        assertThrows(UnauthorizedException.class, () ->
                depenseService.createDepense(depenseRequest, 2L));
    }

    @Test
    void updateDepense_ShouldUpdateExistingDepense() {
        when(depenseRepository.findById(1L)).thenReturn(Optional.of(depense));
        when(depenseRepository.save(any(Depense.class))).thenReturn(depense);
        when(depenseMapper.toResponse(any(Depense.class))).thenReturn(depenseResponse);

        DepenseResponse result = depenseService.updateDepense(depenseRequest, 1L, 1L);

        assertThat(result).isNotNull();
        verify(depenseMapper).updateDepenseFromRequest(depenseRequest, depense);
        verify(depenseRepository).save(depense);
    }

    @Test
    void updateDepense_ShouldThrowException_WhenDepenseValidated() {
        depense.setStatut(DepenseStatut.VALIDEE);
        when(depenseRepository.findById(1L)).thenReturn(Optional.of(depense));

        assertThrows(IllegalStateException.class, () ->
                depenseService.updateDepense(depenseRequest, 1L, 1L));
    }

    @Test
    void deleteDepense_ShouldDeleteDepense() {
        when(depenseRepository.findById(1L)).thenReturn(Optional.of(depense));

        depenseService.deleteDepense(1L, 1L);

        verify(depenseRepository).delete(depense);
    }

    @Test
    void deleteDepense_ShouldThrowException_WhenDepenseValidated() {
        depense.setStatut(DepenseStatut.VALIDEE);
        when(depenseRepository.findById(1L)).thenReturn(Optional.of(depense));

        assertThrows(IllegalStateException.class, () ->
                depenseService.deleteDepense(1L, 1L));
    }

    @Test
    void validateDepense_ShouldValidateDepense() {
        when(depenseRepository.findById(1L)).thenReturn(Optional.of(depense));
        when(depenseRepository.save(any(Depense.class))).thenReturn(depense);
        when(depenseMapper.toResponse(any(Depense.class))).thenReturn(depenseResponse);

        DepenseResponse result = depenseService.validateDepense(1L, DepenseStatut.VALIDEE);

        assertThat(result).isNotNull();
        assertThat(depense.getStatut()).isEqualTo(DepenseStatut.VALIDEE);
        verify(depenseRepository).save(depense);
    }

    @Test
    void validateDepense_ShouldThrowException_WhenInvalidStatus() {
        assertThrows(IllegalArgumentException.class, () ->
                depenseService.validateDepense(1L, DepenseStatut.EN_ATTENTE));
    }
}