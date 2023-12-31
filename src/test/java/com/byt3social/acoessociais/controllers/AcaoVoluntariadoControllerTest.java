package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.dto.OpcaoContribuicaoDTO;
import com.byt3social.acoessociais.dto.PDSignProcessoDTO;
import com.byt3social.acoessociais.enums.*;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import com.byt3social.acoessociais.services.AcaoVoluntariadoService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.sql.Time;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

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
                LocalDate.now(),
                LocalDate.now(),
            time.toString(),
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
            null,
            123,
            456,
                1
        );
    }

    @Test
    public void testCadastrarAcaoVoluntariado() {
        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createAcaoVoluntariadoDTO();
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

    @Test
    public void testConsultarInscricoesAcaoVoluntariado() {
        int acaoVoluntariadoID = 1;
        List<Inscricao> inscricoes = new ArrayList<>(); 
        Mockito.when(acaoVoluntariadoService.consultarInscricoesAcaoVoluntariado(acaoVoluntariadoID)).thenReturn(inscricoes);

        ResponseEntity<List<Inscricao>> response = acaoVoluntariadoController.consultarInscricoesAcaoVoluntariado(acaoVoluntariadoID);

        Mockito.verify(acaoVoluntariadoService).consultarInscricoesAcaoVoluntariado(acaoVoluntariadoID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(inscricoes, response.getBody());
    }

    @Test
    public void testExcluirAcaoVoluntariado() {
        int acaoVoluntariadoID = 1;
        Mockito.doNothing().when(acaoVoluntariadoService).excluirAcaoVoluntariado(acaoVoluntariadoID);

        ResponseEntity response = acaoVoluntariadoController.excluirAcaoVoluntariado(acaoVoluntariadoID);

        Mockito.verify(acaoVoluntariadoService).excluirAcaoVoluntariado(acaoVoluntariadoID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testSalvarArquivoAcaoVoluntariado() {
        int acaoVoluntariadoID = 1;
        MultipartFile imagem = Mockito.mock(MultipartFile.class); 
        String urlImagem = "caminho/para/imagem.jpg"; 
        Mockito.when(acaoVoluntariadoService.salvarImagem(acaoVoluntariadoID, imagem)).thenReturn(urlImagem);

        ResponseEntity<String> response = acaoVoluntariadoController.salvarArquivoAcaoVoluntariado(acaoVoluntariadoID, imagem);

        Mockito.verify(acaoVoluntariadoService).salvarImagem(acaoVoluntariadoID, imagem);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(urlImagem, response.getBody());
    }

    @Test
    public void testExcluirArquivoAcaoVoluntariado() {
        
        int acaoVoluntariadoID = 1;
        Mockito.doNothing().when(acaoVoluntariadoService).excluirImagem(acaoVoluntariadoID);

        ResponseEntity response = acaoVoluntariadoController.excluirArquivoAcaoVoluntariado(acaoVoluntariadoID);

        Mockito.verify(acaoVoluntariadoService).excluirImagem(acaoVoluntariadoID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testAdicionarOpcaoContribuicaoAcaoVoluntariado() {

        int acaoVoluntariadoID = 1;
        OpcaoContribuicaoDTO opcaoContribuicaoDTO = new OpcaoContribuicaoDTO(acaoVoluntariadoID, 55.0, null); // Crie um objeto OpcaoContribuicaoDTO para simular a entrada
        Mockito.doNothing().when(acaoVoluntariadoService).adicionarOpcaoContribuicao(acaoVoluntariadoID, opcaoContribuicaoDTO);

        ResponseEntity response = acaoVoluntariadoController.adicionarOpcaoContribuicaoAcaoVoluntariado(acaoVoluntariadoID, opcaoContribuicaoDTO);

        Mockito.verify(acaoVoluntariadoService).adicionarOpcaoContribuicao(acaoVoluntariadoID, opcaoContribuicaoDTO);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testExcluirOpcaoContribuicaoAcaoVoluntariado() {

        int opcaoContribuicaoID = 1;
        Mockito.doNothing().when(acaoVoluntariadoService).excluirOpcaoContribuicao(opcaoContribuicaoID);

        ResponseEntity response = acaoVoluntariadoController.excluirOpcaoContribuicaoAcaoVoluntariado(opcaoContribuicaoID);

        Mockito.verify(acaoVoluntariadoService).excluirOpcaoContribuicao(opcaoContribuicaoID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testBuscarAcoesParaDivulgacao() {

        Map<String, List<AcaoVoluntariado>> acoes = new HashMap<>();
        List<AcaoVoluntariado> acoesVoluntariado = new ArrayList<>(); 
        List<AcaoVoluntariado> acoesDoacao = new ArrayList<>(); 
        acoes.put("voluntariado", acoesVoluntariado);
        acoes.put("doacao", acoesDoacao);

        Mockito.when(acaoVoluntariadoService.buscarAcoesParaDivulgacao()).thenReturn(acoes);
        ResponseEntity<Map<String, List<AcaoVoluntariado>>> response = acaoVoluntariadoController.buscarAcoesParaDivulgacao();

        Mockito.verify(acaoVoluntariadoService).buscarAcoesParaDivulgacao();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testConsultarProcessoPDSign() {
        int acaoId = 1;
        List<PDSignProcessoDTO> pdSignProcessoDTOList = new ArrayList<>();
        Mockito.when(acaoVoluntariadoService.consultarProcessoPDSign(acaoId)).thenReturn(pdSignProcessoDTOList);

        ResponseEntity<List<PDSignProcessoDTO>> response = acaoVoluntariadoController.consultarProcessoPDSign(acaoId);

        Mockito.verify(acaoVoluntariadoService).consultarProcessoPDSign(acaoId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

}
