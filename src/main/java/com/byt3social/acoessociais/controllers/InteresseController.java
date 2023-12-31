package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.services.InteresseService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/interesses")
public class InteresseController {
    @Autowired
    private InteresseService interesseService;

    @Operation(summary = "Manifestar interesse")
    @ApiResponse(responseCode = "204", description = "Interesse manifestado com sucesso!")
    @PostMapping
    public ResponseEntity manifestarInteresse(@RequestHeader("B3Social-Colaborador") String colaboradorId, @RequestBody InteresseDTO interesseDTO) {
        interesseService.manisfestarInteresse(interesseDTO, Integer.valueOf(colaboradorId));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Consultar interesses de um colaborador")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Integer.class)))
    @GetMapping
    public ResponseEntity consultarInteresses(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Integer> interesses = interesseService.consultarInteresses(Integer.valueOf(colaboradorId));

        return new ResponseEntity(interesses, HttpStatus.OK);
    }
}
