package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InscricaoDTO;
import com.byt3social.acoessociais.services.InscricaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
public class InscricaoControllerTest {

    @Autowired
    private InscricaoController inscricaoController;

    @MockBean
    private InscricaoService inscricaoService;

    @Test
    public void testRealizarInscricao() {
        InscricaoDTO inscricaoDTO = new InscricaoDTO(1);

        doNothing().when(inscricaoService).realizarInscricao(inscricaoDTO, 1);

        ResponseEntity response = inscricaoController.realizarInscricao(String.valueOf(1), inscricaoDTO);

        verify(inscricaoService).realizarInscricao(inscricaoDTO, 1);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCancelarInscricao() {
        int inscricaoID = 1;

        doNothing().when(inscricaoService).cancelarInscricao(inscricaoID);

        ResponseEntity response = inscricaoController.cancelarInscricao(inscricaoID);

        verify(inscricaoService).cancelarInscricao(inscricaoID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testConsultarInscricoes() {

        String colaboradorId = "1";
        List<Map> inscricoes = new ArrayList<>(); 
        when(inscricaoService.consultarInscricoes(Integer.valueOf(colaboradorId))).thenReturn(inscricoes);

        ResponseEntity<List<Map>> response = inscricaoController.consultarInscricoes(colaboradorId);

        verify(inscricaoService).consultarInscricoes(Integer.valueOf(colaboradorId));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testConsultarInscricoesPorAcaoId() {

        String colaboradorId = "1";
        List<Integer> inscricoes = new ArrayList<>(); 
        when(inscricaoService.consultarInscricoesPorAcaoId(Integer.valueOf(colaboradorId))).thenReturn(inscricoes);

        ResponseEntity<List<Integer>> response = inscricaoController.consultarInscricoesPorAcaoId(colaboradorId);

        verify(inscricaoService).consultarInscricoesPorAcaoId(Integer.valueOf(colaboradorId));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
