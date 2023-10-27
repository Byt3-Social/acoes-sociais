package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.models.Doacao;
import com.byt3social.acoessociais.services.DoacaoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
            100.0
        );

        ResponseEntity<Doacao> response = doacaoController.realizarDoacao(String.valueOf(1), doacaoDTO);

        verify(doacaoService).realizarDoacao(doacaoDTO, 1);
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

    @Test
    public void testConsultarDoacoes() {

        String colaboradorId = "1";
        List<Map> doacoes = new ArrayList<>(); 
        Mockito.when(doacaoService.consultarDoacoes(Integer.valueOf(colaboradorId))).thenReturn(doacoes);

        ResponseEntity<List<Map>> response = doacaoController.consultarDoacoes(colaboradorId);

        Mockito.verify(doacaoService).consultarDoacoes(Integer.valueOf(colaboradorId));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

@Test
public void testConsultarDoacao() {

    int doacaoID = 1;
    Doacao doacao = new Doacao(); 
    Mockito.when(doacaoService.consultarDoacao(doacaoID)).thenReturn(doacao);

    ResponseEntity<Doacao> response = doacaoController.consultarDoacao(doacaoID);

    Mockito.verify(doacaoService).consultarDoacao(doacaoID);
    assertEquals(HttpStatus.OK, response.getStatusCode());
}

@Test
public void testEstatisticas() {

    int acaoID = 1;
    Map<String, Object> estatisticas = new HashMap<>(); 

    Mockito.when(doacaoService.gerarEstatisticas(acaoID)).thenReturn(estatisticas);
    ResponseEntity<Map<String, Object>> response = doacaoController.estatisticas(acaoID);

    Mockito.verify(doacaoService).gerarEstatisticas(acaoID);
    assertEquals(HttpStatus.OK, response.getStatusCode());
}
}
