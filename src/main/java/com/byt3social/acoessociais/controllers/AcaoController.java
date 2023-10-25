package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Acao;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.services.AcaoService;
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

    @PostMapping("/acoes/{id}/arquivos")
    public ResponseEntity salvarArquivoAcao(@RequestHeader("Authorization") String token, @PathVariable("id") Integer acaoID, @RequestParam("acao") String tipoAcao, @RequestParam("upload") String upload, @RequestBody MultipartFile arquivo) {
        Object arquivoEnviado = acaoService.salvarArquivo(acaoID, tipoAcao, upload, arquivo, token);

        return new ResponseEntity(arquivoEnviado, HttpStatus.CREATED);
    }

    @GetMapping("/acoes-isp/organizacoes")
    public ResponseEntity buscarAcoesOrganizacao(@RequestHeader("B3Social-Organizacao") String organizacaoId) {
        List<AcaoISP> acoes = acaoService.buscarAcoes(Integer.valueOf(organizacaoId));

        return new ResponseEntity(acoes, HttpStatus.OK);
    }

    @GetMapping("/acoes")
    public ResponseEntity buscarAcoes() {
        List<Acao> acoes = acaoService.buscarAcoes();

        return new ResponseEntity(acoes, HttpStatus.OK);
    }

    @GetMapping("/acoes/arquivos/{id}")
    public ResponseEntity recuperarArquivoAcao(@PathVariable("id") Integer arquivoID, @RequestParam("download") String download) {
        String urlArquivo = acaoService.recuperarArquivo(arquivoID, download);

        return new ResponseEntity(urlArquivo, HttpStatus.OK);
    }

    @DeleteMapping("/acoes/arquivos/{id}")
    public ResponseEntity excluirArquivoAcao(@PathVariable("id") Integer arquivoID, @RequestParam(value = "tipo") String tipo) {
        acaoService.excluirArquivo(arquivoID, tipo);

        return new ResponseEntity(HttpStatus.OK);
    }

    
}
