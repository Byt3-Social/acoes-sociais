package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.SegmentoDTO;
import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.repositories.SegmentoRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/segmentos")
public class SegmentoController {
    @Autowired
    private SegmentoRepository segmentoRepository;

    @Operation(summary = "Cadastrar um segmento")
    @ApiResponse(responseCode = "201", description = "Segmento cadastrado com sucesso!")
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarSegmento(@Valid @RequestBody SegmentoDTO segmentoDTO) {
        Segmento segmento = new Segmento(segmentoDTO);
        segmentoRepository.save(segmento);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Consultar todos os segmentos")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Segmento.class)))
    @GetMapping
    public ResponseEntity consultarSegmentos() {
        List<Segmento> segmentos = segmentoRepository.findAll();

        return new ResponseEntity(segmentos, HttpStatus.OK);
    }

    @Operation(summary = "Consultar um segmento específico")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Segmento.class)))
    @ApiResponse(responseCode = "404", description = "Segmento não encontrado")
    @GetMapping("/{id}")
    public ResponseEntity consultarSegmento(@PathVariable("id") Integer segmentoID) {
        Segmento segmento = segmentoRepository.findById(segmentoID).get();

        return new ResponseEntity(segmento, HttpStatus.OK);
    }

    @Operation(summary = "Excluir um segmento")
    @ApiResponse(responseCode = "200", description = "Segmento excluído com sucesso!")
    @ApiResponse(responseCode = "404", description = "Segmento não encontrado")
    @DeleteMapping("/{id}")
    public ResponseEntity excluirSegmento(@PathVariable("id") Integer segmentoID) {
        segmentoRepository.deleteById(segmentoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
