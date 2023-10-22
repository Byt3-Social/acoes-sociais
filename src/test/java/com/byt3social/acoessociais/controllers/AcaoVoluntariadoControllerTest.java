package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.controllers.AcaoVoluntariadoController;
import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.enums.Fase;
import com.byt3social.acoessociais.enums.Formato;
import com.byt3social.acoessociais.enums.Nivel;
import com.byt3social.acoessociais.enums.Tipo;
import com.byt3social.acoessociais.enums.TipoMeta;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.services.AcaoVoluntariadoService;
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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class AcaoVoluntariadoControllerTest {

    @Autowired
    private AcaoVoluntariadoController acaoVoluntariadoController;

    @MockBean
    private AcaoVoluntariadoService acaoVoluntariadoService;

    Time time = new Time(System.currentTimeMillis());

    private AcaoVoluntariadoDTO createAcaoVoluntariadoDTO() {
        return new AcaoVoluntariadoDTO(
            "Minha Ação Voluntariado",
            Nivel.N1,
            Fase.CRIADA,
            Formato.PRESENCIAL,
            Tipo.MENTORIA,
            new Date(),
            new Date(),
            time,
            "Local da Ação",
            "Informações adicionais",
            100,
            500.0,
            TipoMeta.DOACOES,
            true,
            true,
            false,
            2,
            "Sobre a organização",
            "Sobre a ação",
            1,
            123,
            456
        );
    }

    @Test
    public void testCadastrarAcaoVoluntariado() {
        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createAcaoVoluntariadoDTO();
        doNothing().when(acaoVoluntariadoService).cadastrarAcaoVoluntariado(acaoVoluntariadoDTO);

        ResponseEntity response = acaoVoluntariadoController.cadastrarAcaoVoluntariado(acaoVoluntariadoDTO);

        verify(acaoVoluntariadoService).cadastrarAcaoVoluntariado(acaoVoluntariadoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testAtualizarAcaoVoluntariado() {
        int acaoVoluntariadoID = 1;
        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createAcaoVoluntariadoDTO();
        doNothing().when(acaoVoluntariadoService).atualizarAcaoVoluntariado(acaoVoluntariadoID, acaoVoluntariadoDTO);

        ResponseEntity response = acaoVoluntariadoController.atualizarAcaoVoluntariado(acaoVoluntariadoID, acaoVoluntariadoDTO);

        verify(acaoVoluntariadoService).atualizarAcaoVoluntariado(acaoVoluntariadoID, acaoVoluntariadoDTO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }

    @Test
    public void testConsultarAcoesVoluntariado() {
        List<AcaoVoluntariado> acoesVoluntariado = new ArrayList<>();
        AcaoVoluntariadoDTO acaoVoluntariadoDTO1 = createAcaoVoluntariadoDTO();
        AcaoVoluntariadoDTO acaoVoluntariadoDTO2 = createAcaoVoluntariadoDTO();
        AcaoVoluntariado acaoVoluntariado1 = new AcaoVoluntariado(acaoVoluntariadoDTO1);
        AcaoVoluntariado acaoVoluntariado2 = new AcaoVoluntariado(acaoVoluntariadoDTO2);

        acoesVoluntariado.add(acaoVoluntariado1);
        acoesVoluntariado.add(acaoVoluntariado2);

        when(acaoVoluntariadoService.consultarAcoesVoluntariado()).thenReturn(acoesVoluntariado);

        ResponseEntity<List<AcaoVoluntariado>> response = acaoVoluntariadoController.consultarAcoesVoluntariado();

        verify(acaoVoluntariadoService).consultarAcoesVoluntariado();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<AcaoVoluntariado> returnedAcoes = response.getBody();
        assertEquals(2, returnedAcoes.size());
    }

    @Test
    public void testConsultarAcaoVoluntariado() {
        int acaoVoluntariadoID = 1;
        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createAcaoVoluntariadoDTO();
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        when(acaoVoluntariadoService.consultarAcaoVoluntariado(acaoVoluntariadoID)).thenReturn(acaoVoluntariado);

        ResponseEntity response = acaoVoluntariadoController.consultarAcaoVoluntariado(acaoVoluntariadoID);

        verify(acaoVoluntariadoService).consultarAcaoVoluntariado(acaoVoluntariadoID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
