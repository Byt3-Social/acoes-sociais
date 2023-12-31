package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InscricaoDTO;
import com.byt3social.acoessociais.services.InscricaoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/inscricoes")
public class InscricaoController {
    @Autowired
    private InscricaoService inscricaoService;

    @Operation(summary = "Realizar inscrição")
    @ApiResponse(responseCode = "201", description = "Inscrição realizada com sucesso!")
    @PostMapping
    public ResponseEntity realizarInscricao(@RequestHeader("B3Social-Colaborador") String colaboradorId, @RequestBody InscricaoDTO inscricaoDTO) {
        inscricaoService.realizarInscricao(inscricaoDTO, Integer.valueOf(colaboradorId));

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Consultar inscrições de um colaborador")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping
    public ResponseEntity consultarInscricoes(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Map> inscricoes = inscricaoService.consultarInscricoes(Integer.valueOf(colaboradorId));

        return new ResponseEntity(inscricoes, HttpStatus.OK);
    }

    @Operation(summary = "Consultar inscrições por ID de ação")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Integer.class)))
    @GetMapping("/acoes")
    public ResponseEntity consultarInscricoesPorAcaoId(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Integer> inscricoes = inscricaoService.consultarInscricoesPorAcaoId(Integer.valueOf(colaboradorId));

        return new ResponseEntity(inscricoes, HttpStatus.OK);
    }

    @Operation(summary = "Cancelar inscrição")
    @ApiResponse(responseCode = "200", description = "Inscrição cancelada com sucesso!")
    @ApiResponse(responseCode = "404", description = "Inscrição não encontrado")
    @DeleteMapping("/{inscricao}")
    public ResponseEntity cancelarInscricao(@PathVariable("inscricao") Integer inscricaoID) {
        inscricaoService.cancelarInscricao(inscricaoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
