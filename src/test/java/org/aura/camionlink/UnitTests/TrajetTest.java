package org.aura.camionlink.UnitTests;

import org.aura.camionlink.DTO.TrajetRequest;
import org.aura.camionlink.DTO.TrajetResponse;
import org.aura.camionlink.Entities.Camion;
import org.aura.camionlink.Entities.Cargaison;
import org.aura.camionlink.Entities.Conducteur;
import org.aura.camionlink.Entities.Enums.CargaisonType;
import org.aura.camionlink.Entities.Enums.ConducteurStatut;
import org.aura.camionlink.Entities.Enums.TrajetStatut;
import org.aura.camionlink.Entities.Trajet;
import org.aura.camionlink.Exceptions.TrajetException;
import org.aura.camionlink.Mapper.TrajetMapper;
import org.aura.camionlink.Repositories.CamionRepo;
import org.aura.camionlink.Repositories.CargaisonRepo;
import org.aura.camionlink.Repositories.ConducteurRepo;
import org.aura.camionlink.Repositories.TrajetRepo;
import org.aura.camionlink.Services.Implementation.TrajetServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class TrajetTest {

    @Mock
    private TrajetRepo trajetRepository;

    @Mock
    private ConducteurRepo conducteurRepository;

    @Mock
    private CamionRepo camionRepository;

    @Mock
    private CargaisonRepo cargaisonRepository;

    @Mock
    private TrajetMapper trajetMapper;

    @InjectMocks
    private TrajetServiceImpl trajetService;

    private TrajetRequest trajetRequest;
    private Trajet trajet;
    private TrajetResponse trajetResponse;
    private Conducteur conducteur;
    private Camion camion;
    private Cargaison cargaison;

    @BeforeEach
    void setUp() {
        Long conducteurId = 1L;
        Long camionId = 1L;
        Long cargaisonId = 1L;

        trajetRequest = TrajetRequest.builder()
                .dateDepart(LocalDateTime.now())
                .dateArrivee(LocalDateTime.now().plusDays(2))
                .pointDepart("Casablanca")
                .pointArrivee("Rabat")
                .conducteurId(conducteurId)
                .camionId(camionId)
                .cargaisonId(cargaisonId)
                .build();

        conducteur = new Conducteur();
        conducteur.setId(conducteurId);
        conducteur.setNom("Dupont");
        conducteur.setPrenom("Jean");
        conducteur.setDisponibilite(ConducteurStatut.DISPONIBLE);

        camion = new Camion();
        camion.setId(camionId);
        camion.setMarque("Volvo");
        camion.setModele("FH16");

        cargaison = new Cargaison();
        cargaison.setId(cargaisonId);
        cargaison.setType(CargaisonType.FRAGILE);

        trajet = new Trajet();
        trajet.setId(1L);
        trajet.setDateDepart(trajetRequest.dateDepart());
        trajet.setDateArrivee(trajetRequest.dateArrivee());
        trajet.setPointDepart(trajetRequest.pointDepart());
        trajet.setPointArrivee(trajetRequest.pointArrivee());
        trajet.setConducteur(conducteur);
        trajet.setCamion(camion);
        trajet.setCargaison(cargaison);
        trajet.setStatut(TrajetStatut.EN_ATTENTE);

        trajetResponse = TrajetResponse.builder()
                .id(1L)
                .dateDepart(trajet.getDateDepart())
                .dateArrivee(trajet.getDateArrivee())
                .pointDepart(trajet.getPointDepart())
                .pointArrivee(trajet.getPointArrivee())
                .statut(TrajetStatut.EN_ATTENTE)
                .build();
    }

    @Test
    void testCreateTrajet() {
        when(conducteurRepository.findById(trajetRequest.conducteurId())).thenReturn(Optional.of(conducteur));
        when(camionRepository.findById(trajetRequest.camionId())).thenReturn(Optional.of(camion));
        when(cargaisonRepository.findById(trajetRequest.cargaisonId())).thenReturn(Optional.of(cargaison));
        when(trajetMapper.toEntity(any(TrajetRequest.class))).thenReturn(trajet);
        when(trajetRepository.save(any(Trajet.class))).thenReturn(trajet);
        when(trajetMapper.toResponse(any(Trajet.class))).thenReturn(trajetResponse);

        TrajetResponse result = trajetService.createTrajet(trajetRequest);

        assertNotNull(result);
        assertEquals(trajetResponse.id(), result.id());
        assertEquals(trajetResponse.pointDepart(), result.pointDepart());
        assertEquals(trajetResponse.pointArrivee(), result.pointArrivee());

        verify(conducteurRepository).findById(trajetRequest.conducteurId());
        verify(camionRepository).findById(trajetRequest.camionId());
        verify(cargaisonRepository).findById(trajetRequest.cargaisonId());
        verify(trajetMapper).toEntity(trajetRequest);
        verify(trajetRepository).save(trajet);
        verify(trajetMapper).toResponse(trajet);
    }

    @Test
    void testGetTrajetById() {
        Long id = 1L;
        when(trajetRepository.findById(id)).thenReturn(Optional.of(trajet));
        when(trajetMapper.toResponse(trajet)).thenReturn(trajetResponse);

        TrajetResponse result = trajetService.getTrajetById(id);

        assertNotNull(result);
        assertEquals(trajetResponse.id(), result.id());

        verify(trajetRepository).findById(id);
        verify(trajetMapper).toResponse(trajet);
    }

    @Test
    void testGetTrajetById_NotFound() {
        Long id = 999L;
        when(trajetRepository.findById(id)).thenReturn(Optional.empty());
        assertThrows(TrajetException.class, () -> trajetService.getTrajetById(id));

        verify(trajetRepository).findById(id);
    }

    @Test
    void testGetAllTrajets() {
        List<Trajet> trajets = List.of(trajet);
        when(trajetRepository.findAll()).thenReturn(trajets);
        when(trajetMapper.toResponse(any(Trajet.class))).thenReturn(trajetResponse);

        List<TrajetResponse> results = trajetService.getAllTrajets();

        assertNotNull(results);
        assertEquals(1, results.size());

        verify(trajetRepository).findAll();
        verify(trajetMapper).toResponse(trajet);
    }

    @Test
    void testUpdateTrajet() {
        Long id = 1L;
        when(trajetRepository.findById(id)).thenReturn(Optional.of(trajet));
        when(conducteurRepository.findById(trajetRequest.conducteurId())).thenReturn(Optional.of(conducteur));
        when(camionRepository.findById(trajetRequest.camionId())).thenReturn(Optional.of(camion));
        when(cargaisonRepository.findById(trajetRequest.cargaisonId())).thenReturn(Optional.of(cargaison));
        doNothing().when(trajetMapper).updateTrajetFromRequest(trajetRequest, trajet);
        when(trajetRepository.save(any(Trajet.class))).thenReturn(trajet);
        when(trajetMapper.toResponse(any(Trajet.class))).thenReturn(trajetResponse);

        TrajetResponse result = trajetService.updateTrajet(id, trajetRequest);

        assertNotNull(result);
        assertEquals(trajetResponse.id(), result.id());

        verify(trajetRepository).findById(id);
        verify(trajetMapper).updateTrajetFromRequest(trajetRequest, trajet);
        verify(trajetRepository).save(trajet);
        verify(trajetMapper).toResponse(trajet);
    }

    @Test
    void testDeleteTrajet() {
        Long id = 1L;
        when(trajetRepository.findById(id)).thenReturn(Optional.of(trajet));

        trajetService.deleteTrajet(id);

        verify(trajetRepository).findById(id);
        verify(trajetRepository).deleteById(id);
    }

    @Test
    void testGetConducteurTrajets() {
        Long conducteurId = 1L;
        when(conducteurRepository.findById(conducteurId)).thenReturn(Optional.of(conducteur));
        when(trajetRepository.findByConducteur(conducteur)).thenReturn(List.of(trajet));
        when(trajetMapper.toResponse(any(Trajet.class))).thenReturn(trajetResponse);

        List<TrajetResponse> results = trajetService.getConducteurTrajets(conducteurId);

        assertNotNull(results);
        assertEquals(1, results.size());

        verify(conducteurRepository).findById(conducteurId);
        verify(trajetRepository).findByConducteur(conducteur);
        verify(trajetMapper).toResponse(trajet);
    }

    @Test
    void testUpdateTrajetStatus() {
        Long conducteurId = 1L;
        Long trajetId = 1L;
        TrajetStatut newStatus = TrajetStatut.EN_COURS;

        when(trajetRepository.findById(trajetId)).thenReturn(Optional.of(trajet));
        when(trajetRepository.save(any(Trajet.class))).thenReturn(trajet);
        when(trajetMapper.toResponse(any(Trajet.class))).thenReturn(trajetResponse);

        TrajetResponse result = trajetService.updateTrajetStatus(conducteurId, trajetId, newStatus);

        assertNotNull(result);

        verify(trajetRepository).findById(trajetId);
        verify(trajetRepository).save(trajet);
        verify(trajetMapper).toResponse(trajet);
    }

    @Test
    void testGetTrajetCount() {
        when(trajetRepository.count()).thenReturn(5L);

        Long count = trajetService.getTrajetCount();

        assertEquals(5L, count);

        verify(trajetRepository).count();
    }

}
