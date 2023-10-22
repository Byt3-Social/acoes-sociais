package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InscricaoDTO;
import com.byt3social.acoessociais.services.InscricaoService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InscricaoControllerTest {

    @Autowired
    private InscricaoController inscricaoController;

    @MockBean
    private InscricaoService inscricaoService;

    @Test
    public void testRealizarInscricao() {
        InscricaoDTO inscricaoDTO = new InscricaoDTO(1, 2);

        doNothing().when(inscricaoService).realizarInscricao(inscricaoDTO);

        ResponseEntity response = inscricaoController.realizarInscricao(inscricaoDTO);

        verify(inscricaoService).realizarInscricao(inscricaoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testCancelarInscricao() {
        int inscricaoID = 1;

        doNothing().when(inscricaoService).cancelarInscricao(inscricaoID);

        ResponseEntity response = inscricaoController.cancelarInscricao(inscricaoID);

        verify(inscricaoService).cancelarInscricao(inscricaoID);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
