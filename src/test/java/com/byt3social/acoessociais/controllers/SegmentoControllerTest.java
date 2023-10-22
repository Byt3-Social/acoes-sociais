package com.byt3social.acoessociais.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.byt3social.acoessociais.dto.SegmentoDTO;
import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.repositories.SegmentoRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class SegmentoControllerTest {

    @Autowired
    private SegmentoController segmentoController;

    @MockBean
    private SegmentoRepository segmentoRepository;

    @Test
    public void testCadastrarSegmento() {
        SegmentoDTO segmentoDTO = new SegmentoDTO("Test Segment");
        Segmento segmento = new Segmento(segmentoDTO);

        when(segmentoRepository.save(any(Segmento.class))).thenReturn(segmento);

        ResponseEntity response = segmentoController.cadastrarSegmento(segmentoDTO);

        verify(segmentoRepository).save(any(Segmento.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }


    @Test
    public void testConsultarSegmentos() {
        List<Segmento> segmentos = new ArrayList<>();
        segmentos.add(new Segmento(new SegmentoDTO("Segment 1")));
        segmentos.add(new Segmento(new SegmentoDTO("Segment 2")));

        when(segmentoRepository.findAll()).thenReturn(segmentos);

        ResponseEntity response = segmentoController.consultarSegmentos();

        verify(segmentoRepository).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testConsultarSegmento() {
        Integer segmentoId = 1;
        Segmento segmento = new Segmento(new SegmentoDTO("Test Segment"));
        
        when(segmentoRepository.findById(segmentoId)).thenReturn(Optional.of(segmento));

        ResponseEntity response = segmentoController.consultarSegmento(segmentoId);

        verify(segmentoRepository).findById(segmentoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testExcluirSegmento() {
        Integer segmentoId = 1;

        doNothing().when(segmentoRepository).deleteById(segmentoId);

        ResponseEntity response = segmentoController.excluirSegmento(segmentoId);

        verify(segmentoRepository).deleteById(segmentoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
