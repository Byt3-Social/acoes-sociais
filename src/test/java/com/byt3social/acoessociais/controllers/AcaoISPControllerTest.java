package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.enums.Abrangencia;
import com.byt3social.acoessociais.enums.StatusISP;
import com.byt3social.acoessociais.enums.TipoInvestimento;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.services.AcaoISPService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AcaoISPControllerTest {

    @MockBean
    private AcaoISPService acaoISPService;

    @Autowired
    private AcaoISPController acaoISPController;

    private AcaoISPDTO createAcaoISPDTO() {
        return new AcaoISPDTO(
            "Acao Test",
            "Description",
            Abrangencia.NACIONAL,
            TipoInvestimento.PRIVADO,
            1000,
            10000.0,
            StatusISP.EM_ANDAMENTO,
            List.of("Location1", "Location2"),
            null,
            1,
            2,
            3,
            4
        );
    }

    @Test
    public void testConsultarAcoesISP() {
        List<AcaoISP> umaListaDeAcoesISP = new ArrayList<>();

        AcaoISPDTO acaoISPDTO1 = createAcaoISPDTO();
        AcaoISPDTO acaoISPDTO2 = createAcaoISPDTO();

        AcaoISP acaoISP1 = new AcaoISP(acaoISPDTO1, null, null, null);
        acaoISP1.setId(1);
        AcaoISP acaoISP2 = new AcaoISP(acaoISPDTO2, null, null, null);
        acaoISP2.setId(2);

        umaListaDeAcoesISP.add(acaoISP1);
        umaListaDeAcoesISP.add(acaoISP2);

        when(acaoISPService.consultarAcoesISP()).thenReturn(umaListaDeAcoesISP);

        ResponseEntity response = acaoISPController.consultarAcoesISP();

        verify(acaoISPService).consultarAcoesISP();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testConsultarAcaoISP() {
        int acaoISPID = 1;
        AcaoISPDTO acaoISPDTO = createAcaoISPDTO();
        AcaoISP umaAcaoISP = new AcaoISP(acaoISPDTO, null, null, null);
        umaAcaoISP.setId(1);

        when(acaoISPService.consultarAcaoISP(acaoISPID)).thenReturn(umaAcaoISP);
        ResponseEntity response = acaoISPController.consultarAcaoISP(acaoISPID);

        verify(acaoISPService).consultarAcaoISP(acaoISPID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testCadastrarAcaoISP() {
        AcaoISPDTO acaoISPDTO = createAcaoISPDTO();

        ResponseEntity response = acaoISPController.cadastrarAcaoISP(acaoISPDTO);

        verify(acaoISPService).cadastrarAcaoISP(acaoISPDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAtualizarAcaoISP() {
        int acaoISPID = 1;
        AcaoISPDTO acaoISPDTO = createAcaoISPDTO();
        doNothing().when(acaoISPService).atualizarAcaoISP(acaoISPID, acaoISPDTO);

        ResponseEntity response = acaoISPController.atualizarAcaoISP(acaoISPID, acaoISPDTO);

        verify(acaoISPService).atualizarAcaoISP(acaoISPID, acaoISPDTO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testExcluirAcaoISP() {
        int acaoISPID = 1;
        doNothing().when(acaoISPService).excluirAcaoISP(acaoISPID);

        ResponseEntity response = acaoISPController.excluirAcaoISP(acaoISPID);

        verify(acaoISPService).excluirAcaoISP(acaoISPID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
