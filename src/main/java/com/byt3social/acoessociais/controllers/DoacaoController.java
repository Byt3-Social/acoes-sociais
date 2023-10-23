package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.models.Doacao;
import com.byt3social.acoessociais.services.DoacaoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class DoacaoController {
    @Autowired
    private DoacaoService doacaoService;

    @PostMapping("/doacoes")
    public ResponseEntity realizarDoacao(@RequestHeader("B3Social-Colaborador") String colaboradorId, @Valid @RequestBody DoacaoDTO doacaoDTO) {
        Doacao doacao = doacaoService.realizarDoacao(doacaoDTO, Integer.valueOf(colaboradorId));

        return new ResponseEntity(doacao, HttpStatus.CREATED);
    }

    @GetMapping("/doacoes")
    public ResponseEntity consultarDoacoes(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Map> doacoes = doacaoService.consultarDoacoes(Integer.valueOf(colaboradorId));

        return new ResponseEntity(doacoes, HttpStatus.OK);
    }

    @GetMapping("/doacoes/{id}")
    public ResponseEntity consultarDoacao(@PathVariable("id") Integer doacaoID) {
        Doacao doacao = doacaoService.consultarDoacao(doacaoID);

        return new ResponseEntity(doacao, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/doacoes/pagseguro")
    public ResponseEntity atualizarStatusDoacao(@RequestBody PagseguroTransacaoDTO pagseguroTransacaoDTO) {
        doacaoService.processarNotificacao(pagseguroTransacaoDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/doacoes/{id}/cancelamentos")
    public ResponseEntity cancelarDoacao(@PathVariable("id") Integer doacaoID) {
        doacaoService.cancelarDoacao(doacaoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/doacoes/{id}/estatisticas")
    public ResponseEntity estatisticas(@PathVariable("id") Integer acaoID) {
        Map<String, Object> estatisticas = doacaoService.gerarEstatisticas(acaoID);

        return new ResponseEntity<>(estatisticas, HttpStatus.OK);
    }
}
