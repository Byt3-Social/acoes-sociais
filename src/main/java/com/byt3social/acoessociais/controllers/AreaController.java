package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Area;
import com.byt3social.acoessociais.repositories.AreaRepository;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/areas")
public class AreaController {
    @Autowired
    private AreaRepository areaRepository;

    @Operation(summary = "Consultar todas as áreas de ações ISP")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Area.class)))
    @GetMapping
    public ResponseEntity consultarAreasAcaoISP() {
        List<Area> areas = areaRepository.findAll();

        return new ResponseEntity(areas, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar uma área de ações ISP")
    @ApiResponse(responseCode = "201", description = "Área cadastrada com sucesso!")
    @Transactional
    @PostMapping
    public ResponseEntity cadastrarAreaAcaoISP(@RequestBody Map<String, String> areaBody) {
        Area area = new Area(areaBody.get("nome"));
        areaRepository.save(area);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Excluir uma área de ações ISP")
    @ApiResponse(responseCode = "200", description = "Área excluída com sucesso!")
    @ApiResponse(responseCode = "404", description = "Área não encontrada")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity excluirAreaAcaoISP(@PathVariable("id") Integer areaID) {
        areaRepository.deleteById(areaID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
