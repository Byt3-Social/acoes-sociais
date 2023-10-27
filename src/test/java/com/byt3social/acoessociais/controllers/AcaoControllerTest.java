package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Acao;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.services.AcaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
@AutoConfigureMockMvc
public class AcaoControllerTest {

    @MockBean
    private AcaoService acaoService; 

    @Autowired
    private AcaoController acaoController; 

    @Test
    public void testSalvarArquivoAcao() {
        int acaoID = 1;
        String tipoAcao = "isp";
        String upload = "documento";
        MockMultipartFile arquivo = new MockMultipartFile("file", "filename.txt", "text/plain", "conteúdo do arquivo".getBytes());

        ResponseEntity response = acaoController.salvarArquivoAcao(null, acaoID, tipoAcao, upload, arquivo);

        verify(acaoService).salvarArquivo(acaoID, tipoAcao, upload, arquivo, null);
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }
    /*@Test
    public void testSalvarArquivoAcaoSemDados() {
        int acaoID = 1;
        String tipoAcao = "isp";
        String upload = "documento";
        // Simule uma solicitação sem fornecer arquivo
        MockMultipartFile arquivo = null;

        ResponseEntity response = acaoController.salvarArquivoAcao(acaoID, tipoAcao, upload, arquivo);

        // Verifique se a ação de serviço não foi chamada, já que não há arquivo para salvar
        verify(acaoService, never()).salvarArquivo(anyInt(), anyString(), anyString(), any(MultipartFile.class));
        // Verifique se a resposta é um erro ruim de solicitação (HTTP 400)
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
    }
 */

    @Test
    public void testRecuperarArquivoAcao() {
        int arquivoID = 1;
        String download = "documento";
        String urlArquivo = "http://exemplo.com/arquivo";

        when(acaoService.recuperarArquivo(arquivoID, download)).thenReturn(urlArquivo);
        ResponseEntity responseEntity = acaoController.recuperarArquivoAcao(arquivoID, download);

        verify(acaoService).recuperarArquivo(arquivoID, download);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /*@Test
    public void testRecuperarArquivoAcaoInexistente() {

        int arquivoID = 1;
        String download = "documento";

        when(acaoService.recuperarArquivo(arquivoID, download)).thenReturn(null);
        ResponseEntity responseEntity = acaoController.recuperarArquivoAcao(arquivoID, download);

        verify(acaoService).recuperarArquivo(arquivoID, download);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode());
        assertNull(responseEntity.getHeaders().getLocation());
    }*/

    @Test
    public void testExcluirArquivoAcao() {

        int arquivoID = 1;
        String tipo = "documento";

        ResponseEntity responseEntity = acaoController.excluirArquivoAcao(arquivoID, tipo);
        verify(acaoService).excluirArquivo(arquivoID, tipo);
        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }

    /*@Test
    public void testExcluirArquivoAcaoInexistente() {
        int arquivoID = 1234567891;
        String tipo = "documento";
    
        // Simulando um cenário em que o arquivo não existe
        Mockito.doNothing().when(acaoService).excluirArquivo(arquivoID, tipo);
    
        ResponseEntity responseEntity = acaoController.excluirArquivoAcao(arquivoID, tipo);
    
        verify(acaoService).excluirArquivo(arquivoID, tipo);
        assertEquals(HttpStatus.NOT_FOUND, responseEntity.getStatusCode()); // Use o código HTTP apropriado
    }*/

    @Test
    public void testBuscarAcoesOrganizacao() {

        String organizacaoId = "1";
        List<AcaoISP> acoes = new ArrayList<>(); // Preencha com os dados desejados
        when(acaoService.buscarAcoes(Integer.valueOf(organizacaoId))).thenReturn(acoes);

        ResponseEntity<List<AcaoISP>> response = acaoController.buscarAcoesOrganizacao(organizacaoId);

        verify(acaoService).buscarAcoes(Integer.valueOf(organizacaoId));
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }

    @Test
    public void testBuscarAcoes() {

        List<Acao> acoes = new ArrayList<>(); 
        when(acaoService.buscarAcoes()).thenReturn(acoes);

        ResponseEntity<List<Acao>> response = acaoController.buscarAcoes();

        verify(acaoService).buscarAcoes();
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
