package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import com.byt3social.acoessociais.services.AcaoVoluntariadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.List;

@RestController
public class AcaoVoluntariadoController {
    @Autowired
    private AcaoVoluntariadoService acaoVoluntariadoService;

    @PostMapping( "/acoes-voluntariado")
    public ResponseEntity cadastrarAcaoVoluntariado(@Valid @RequestBody AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        acaoVoluntariadoService.cadastrarAcaoVoluntariado(acaoVoluntariadoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping( "/acoes-voluntariado/{id}")
    public ResponseEntity atualizarAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @Valid @RequestBody AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        acaoVoluntariadoService.atualizarAcaoVoluntariado(acaoVoluntariadoID, acaoVoluntariadoDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/acoes-voluntariado")
    public ResponseEntity consultarAcoesVoluntariado() {
        List<AcaoVoluntariado> acoesVoluntariado = acaoVoluntariadoService.consultarAcoesVoluntariado();

        return new ResponseEntity(acoesVoluntariado, HttpStatus.OK);
    }

    @GetMapping("/acoes-voluntariado/{id}")
    public ResponseEntity consultarAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoService.consultarAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(acaoVoluntariado, HttpStatus.OK);
    }

    @GetMapping("/acoes-voluntariado/{id}/inscricoes")
    public ResponseEntity consultarInscricoesAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        List<Inscricao> inscricoes = acaoVoluntariadoService.consultarInscricoesAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(inscricoes, HttpStatus.OK);
    }

    @DeleteMapping("/acoes-voluntariado/{id}")
    public ResponseEntity excluirAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        acaoVoluntariadoService.excluirAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/acoes-voluntariado/{id}/arquivos")
    public ResponseEntity salvarArquivoAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @RequestParam(value = "tipo") String tipo, @RequestBody MultipartFile arquivo) {
        acaoVoluntariadoService.salvarArquivoAcaoVoluntariado(acaoVoluntariadoID, tipo, arquivo);

        return new ResponseEntity(HttpStatus.OK);
    }

    @GetMapping("/acoes-voluntariado/arquivos/{id}")
    public ResponseEntity recuperarArquivoAcaoVoluntariado(@PathVariable("id") Integer arquivoID, @RequestParam(value = "tipo") String tipo) {
        String urlArquivo = acaoVoluntariadoService.recuperarArquivoAcaoVoluntariado(arquivoID, tipo);

        return ResponseEntity.status(HttpStatus.FOUND).location(URI.create(urlArquivo)).build();
    }

    @DeleteMapping("/acoes-voluntariado/arquivos/{id}")
    public ResponseEntity excluirImagemAcaoVoluntariado(@PathVariable("id") Integer arquivoID, @RequestParam(value = "tipo") String tipo) {
        acaoVoluntariadoService.excluirArquivoAcaoVoluntariado(arquivoID, tipo);

        return new ResponseEntity(HttpStatus.OK);
    }
}
