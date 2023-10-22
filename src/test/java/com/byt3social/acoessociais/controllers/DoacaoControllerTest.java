package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.services.DoacaoService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
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
public class DoacaoControllerTest {

    @Autowired
    private DoacaoController doacaoController;

    @MockBean
    private DoacaoService doacaoService;

    
    @Test
    public void testRealizarDoacao() {
        DoacaoDTO doacaoDTO = new DoacaoDTO(
            "John Doe",
            "john@example.com",
            "123",
            "555-5555",
            "123456789",
            MetodoDoacao.CARTAO_CREDITO,
            null,
            "token123",
            "cvv123",
            1,
            100.0,
            123
        );

        doNothing().when(doacaoService).realizarDoacao(doacaoDTO);

        ResponseEntity response = doacaoController.realizarDoacao(doacaoDTO);

        verify(doacaoService).realizarDoacao(doacaoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAtualizarStatusDoacao() {
        PagseguroTransacaoDTO pagseguroTransacaoDTO = new PagseguroTransacaoDTO(
            "12345",
            "reference123",
            1,
            "referenceId123",
            null,
            null
        );

        doNothing().when(doacaoService).processarNotificacao(pagseguroTransacaoDTO);

        ResponseEntity response = doacaoController.atualizarStatusDoacao(pagseguroTransacaoDTO);

        verify(doacaoService).processarNotificacao(pagseguroTransacaoDTO);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCancelarDoacao() {
        int doacaoID = 1;

        doNothing().when(doacaoService).cancelarDoacao(doacaoID);

        ResponseEntity response = doacaoController.cancelarDoacao(doacaoID);

        verify(doacaoService).cancelarDoacao(doacaoID);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}
