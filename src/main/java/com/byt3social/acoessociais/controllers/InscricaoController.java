package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InscricaoDTO;
import com.byt3social.acoessociais.services.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class InscricaoController {
    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping("/inscricoes")
    public ResponseEntity realizarInscricao(@RequestHeader("B3Social-Colaborador") String colaboradorId, @RequestBody InscricaoDTO inscricaoDTO) {
        inscricaoService.realizarInscricao(inscricaoDTO, Integer.valueOf(colaboradorId));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/inscricoes")
    public ResponseEntity consultarInscricoes(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Map> inscricoes = inscricaoService.consultarInscricoes(Integer.valueOf(colaboradorId));

        return new ResponseEntity(inscricoes, HttpStatus.OK);
    }

    @GetMapping("/inscricoes/acoes")
    public ResponseEntity consultarInscricoesPorAcaoId(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Integer> inscricoes = inscricaoService.consultarInscricoesPorAcaoId(Integer.valueOf(colaboradorId));

        return new ResponseEntity(inscricoes, HttpStatus.OK);
    }

    @DeleteMapping("/inscricoes/{inscricao}")
    public ResponseEntity cancelarInscricao(@PathVariable("inscricao") Integer inscricaoID) {
        inscricaoService.cancelarInscricao(inscricaoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
