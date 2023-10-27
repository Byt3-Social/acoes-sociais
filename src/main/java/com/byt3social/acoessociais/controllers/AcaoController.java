package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Acao;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.services.AcaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
public class AcaoController {
    @Autowired
    private AcaoService acaoService;

    @Operation(summary = "Salvar arquivo para uma ação")
    @ApiResponse(responseCode = "201", description = "Arquivo salvo com sucesso")
    @PostMapping("/acoes/{id}/arquivos")
    public ResponseEntity salvarArquivoAcao(@RequestHeader("Authorization") String token, @PathVariable("id") Integer acaoID, @RequestParam("acao") String tipoAcao, @RequestParam("upload") String upload, @RequestBody MultipartFile arquivo) {
        Object arquivoEnviado = acaoService.salvarArquivo(acaoID, tipoAcao, upload, arquivo, token);

        return new ResponseEntity(arquivoEnviado, HttpStatus.CREATED);
    }

    @Operation(summary = "Buscar ações de uma organização")
    @ApiResponse(responseCode = "200", description = "Ações encontradas", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/acoes-isp/organizacoes")
    public ResponseEntity buscarAcoesOrganizacao(@RequestHeader("B3Social-Organizacao") String organizacaoId) {
        List<AcaoISP> acoes = acaoService.buscarAcoes(Integer.valueOf(organizacaoId));

        return new ResponseEntity(acoes, HttpStatus.OK);
    }

    @Operation(summary = "Buscar todas as ações")
    @ApiResponse(responseCode = "200", description = "Ações encontradas", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/acoes")
    public ResponseEntity buscarAcoes() {
        List<Acao> acoes = acaoService.buscarAcoes();

        return new ResponseEntity(acoes, HttpStatus.OK);
    }

    @Operation(summary = "Recuperar arquivo de uma ação")
    @ApiResponse(responseCode = "200", description = "Arquivo recuperado com sucesso")
    @GetMapping("/acoes/arquivos/{id}")
    public ResponseEntity recuperarArquivoAcao(@PathVariable("id") Integer arquivoID, @RequestParam("download") String download) {
        String urlArquivo = acaoService.recuperarArquivo(arquivoID, download);

        return new ResponseEntity(urlArquivo, HttpStatus.OK);
    }

    @Operation(summary = "Excluir arquivo de uma ação")
    @ApiResponse(responseCode = "200", description = "Arquivo excluído com sucesso")
    @DeleteMapping("/acoes/arquivos/{id}")
    public ResponseEntity excluirArquivoAcao(@PathVariable("id") Integer arquivoID, @RequestParam(value = "tipo") String tipo) {
        acaoService.excluirArquivo(arquivoID, tipo);

        return new ResponseEntity(HttpStatus.OK);
    }

    
}
