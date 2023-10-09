package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.services.AcaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
public class AcaoController {
    @Autowired
    private AcaoService acaoService;

    @PostMapping("/acoes/{id}/arquivos")
    public ResponseEntity salvarArquivoAcao(@PathVariable("id") Integer acaoID, @RequestParam("acao") String tipoAcao, @RequestParam("upload") String upload, @RequestBody MultipartFile arquivo) {
        acaoService.salvarArquivo(acaoID, tipoAcao, upload, arquivo);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/acoes/arquivos/{id}")
    public ResponseEntity recuperarArquivoAcao(@PathVariable("id") Integer arquivoID, @RequestParam("download") String download) {
        String urlArquivo = acaoService.recuperarArquivo(arquivoID, download);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlArquivo)).build();
    }

    @DeleteMapping("/acoes/arquivos/{id}")
    public ResponseEntity excluirArquivoAcao(@PathVariable("id") Integer arquivoID, @RequestParam(value = "tipo") String tipo) {
        acaoService.excluirArquivo(arquivoID, tipo);

        return new ResponseEntity(HttpStatus.OK);
    }
}
