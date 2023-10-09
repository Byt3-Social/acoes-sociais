package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.dto.OpcaoContribuicaoDTO;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import com.byt3social.acoessociais.services.AcaoVoluntariadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping("/acoes-voluntariado/{id}/imagens")
    public ResponseEntity salvarArquivoAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @RequestBody MultipartFile imagem) {
        acaoVoluntariadoService.salvarImagem(acaoVoluntariadoID, imagem);

        return new ResponseEntity(HttpStatus.OK);
    }

    @DeleteMapping("/acoes-voluntariado/{id}/imagens")
    public ResponseEntity excluirArquivoAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        acaoVoluntariadoService.excluirImagem(acaoVoluntariadoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @PostMapping("/acoes-voluntariado/{id}/opcoes-contribuicao")
    public ResponseEntity adicionarOpcaoContribuicaoAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @RequestBody OpcaoContribuicaoDTO opcaoContribuicaoDTO) {
        acaoVoluntariadoService.adicionarOpcaoContribuicao(acaoVoluntariadoID, opcaoContribuicaoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/acoes-voluntariado/opcoes-contribuicao/{id}")
    public ResponseEntity excluirOpcaoContribuicaoAcaoVoluntariado(@PathVariable("id") Integer opcaoContribuicaoID) {
        acaoVoluntariadoService.excluirOpcaoContribuicao(opcaoContribuicaoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
