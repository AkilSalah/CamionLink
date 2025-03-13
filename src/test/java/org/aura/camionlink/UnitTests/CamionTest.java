package org.aura.camionlink.UnitTests;

import org.aura.camionlink.DTO.CamionRequest;
import org.aura.camionlink.DTO.CamionResponse;
import org.aura.camionlink.Entities.Camion;
import org.aura.camionlink.Entities.Enums.CamionEtat;
import org.aura.camionlink.Mapper.CamionMapper;
import org.aura.camionlink.Repositories.CamionRepo;
import org.aura.camionlink.Services.Implementation.CamionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CamionTest {

    @Mock
    private CamionRepo camionRepository;

    @Mock
    private CamionMapper camionMapper;

    @InjectMocks
    private CamionServiceImpl camionService;

    private CamionRequest camionRequest;
    private Camion camion;
    private CamionResponse camionResponse;

    @BeforeEach
    void setUp() {
        camionRequest = CamionRequest.builder()
                .marque("Volvo")
                .modele("FH16")
                .annee(LocalDate.of(2020, 1, 1))
                .etat(CamionEtat.DISPONIBLE)
                .build();

        camion = new Camion();
        camion.setId(1L);
        camion.setMarque("Volvo");
        camion.setModele("FH16");
        camion.setAnnee(LocalDate.of(2020, 1, 1));
        camion.setEtat(CamionEtat.DISPONIBLE);

        camionResponse = CamionResponse.builder()
                .id(1L)
                .marque("Volvo")
                .modele("FH16")
                .annee(LocalDate.of(2020, 1, 1))
                .etat(CamionEtat.DISPONIBLE)
                .build();
    }

    @Test
    void testAddCamion() {
        when(camionMapper.toEntity(any(CamionRequest.class))).thenReturn(camion);
        when(camionRepository.save(any(Camion.class))).thenReturn(camion);
        when(camionMapper.tCamionResponse(any(Camion.class))).thenReturn(camionResponse);

        CamionResponse result = camionService.addCamion(camionRequest);

        assertNotNull(result);
        assertEquals(camionResponse.id(), result.id());
        assertEquals(camionResponse.marque(), result.marque());
        assertEquals(camionResponse.modele(), result.modele());

        verify(camionMapper, times(1)).toEntity(camionRequest);
        verify(camionRepository, times(1)).save(camion);
        verify(camionMapper, times(1)).tCamionResponse(camion);
    }

    @Test
    void testGetCamionCount() {
        when(camionRepository.countCamion()).thenReturn(5L);

        Long count = camionService.getCamionCount();

        assertEquals(5L, count);
        verify(camionRepository, times(1)).countCamion();
    }

    @Test
    void testUpdateCamion() {
        Long id = 1L;
        when(camionRepository.findById(id)).thenReturn(Optional.of(camion));
        doNothing().when(camionMapper).updateCamionFromRequest(camionRequest, camion);
        when(camionRepository.save(any(Camion.class))).thenReturn(camion);
        when(camionMapper.tCamionResponse(any(Camion.class))).thenReturn(camionResponse);

        CamionResponse result = camionService.updateCamion(id, camionRequest);

        assertNotNull(result);
        assertEquals(camionResponse.id(), result.id());
        assertEquals(camionResponse.marque(), result.marque());

        verify(camionRepository, times(1)).findById(id);
        verify(camionMapper, times(1)).updateCamionFromRequest(camionRequest, camion);
        verify(camionRepository, times(1)).save(camion);
        verify(camionMapper, times(1)).tCamionResponse(camion);
    }

    @Test
    void testUpdateCamion_NotFound() {
        Long id = 999L;
        when(camionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> camionService.updateCamion(id, camionRequest));
        verify(camionRepository, times(1)).findById(id);
        verify(camionRepository, never()).save(any(Camion.class));
    }

    @Test
    void testDeleteCamion() {
        Long id = 1L;
        when(camionRepository.findById(id)).thenReturn(Optional.of(camion));

        camionService.deleteCamion(id);

        verify(camionRepository, times(1)).findById(id);
        verify(camionRepository, times(1)).deleteById(id);
    }

    @Test
    void testDeleteCamion_NotFound() {
        Long id = 999L;
        when(camionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> camionService.deleteCamion(id));
        verify(camionRepository, times(1)).findById(id);
        verify(camionRepository, never()).deleteById(anyLong());
    }

    @Test
    void testGetCamionById() {
        Long id = 1L;
        when(camionRepository.findById(id)).thenReturn(Optional.of(camion));
        when(camionMapper.tCamionResponse(camion)).thenReturn(camionResponse);

        CamionResponse result = camionService.getCamionById(id);

        assertNotNull(result);
        assertEquals(camionResponse.id(), result.id());
        verify(camionRepository, times(1)).findById(id);
        verify(camionMapper, times(1)).tCamionResponse(camion);
    }

    @Test
    void testGetCamionById_NotFound() {
        Long id = 999L;
        when(camionRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> camionService.getCamionById(id));
        verify(camionRepository, times(1)).findById(id);
    }

    @Test
    void testGetAllCamions() {
        List<Camion> camions = Arrays.asList(
                camion,
                Camion.builder()
                        .id(2L)
                        .marque("Mercedes")
                        .modele("Actros")
                        .annee(LocalDate.of(2019, 5, 15))
                        .etat(CamionEtat.DISPONIBLE)
                        .build()
        );
        when(camionRepository.findAll()).thenReturn(camions);
        when(camionMapper.tCamionResponse(any(Camion.class))).thenReturn(camionResponse);

        List<CamionResponse> results = camionService.getAllCamions();

        assertNotNull(results);
        assertEquals(2, results.size());
        verify(camionRepository, times(1)).findAll();
        verify(camionMapper, times(2)).tCamionResponse(any(Camion.class));
    }
}
