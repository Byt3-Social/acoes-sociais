package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Area;
import com.byt3social.acoessociais.repositories.AreaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class AreaControllerTest {

    @Autowired
    private AreaController areaController;

    @MockBean
    private AreaRepository areaRepository;

    @Test
    public void testConsultarAreasAcaoISP() {
        List<Area> areas = new ArrayList<>();
        Area area1 = new Area("Area 1");
        Area area2 = new Area("Area 2");
        areas.add(area1);
        areas.add(area2);

        when(areaRepository.findAll()).thenReturn(areas);

        ResponseEntity<List<Area>> response = areaController.consultarAreasAcaoISP();

        verify(areaRepository).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Area> returnedAreas = response.getBody();
        assertNotNull(returnedAreas);
        assertEquals(2, returnedAreas.size());
    }

    @Test
    public void testCadastrarAreaAcaoISP() {
        String areaName = "New Area";
        Area area = new Area(areaName);

        when(areaRepository.save(any(Area.class))).thenReturn(area);

        ResponseEntity response = areaController.cadastrarAreaAcaoISP(Map.of("nome", areaName));

        verify(areaRepository).save(any(Area.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testExcluirAreaAcaoISP() {
        int areaId = 1;

        doNothing().when(areaRepository).deleteById(areaId);

        ResponseEntity response = areaController.excluirAreaAcaoISP(areaId);

        verify(areaRepository).deleteById(areaId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
