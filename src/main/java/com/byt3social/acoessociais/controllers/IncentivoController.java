package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Incentivo;
import com.byt3social.acoessociais.repositories.IncentivoRepository;

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
@RequestMapping("/incentivos")
public class IncentivoController {
    @Autowired
    private IncentivoRepository incentivoRepository;

    @Operation(summary = "Consultar todos os incentivos de uma ação ISP")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Incentivo.class)))
    @GetMapping
    public ResponseEntity consultarIncentivosAcaoISP() {
        List<Incentivo> incentivos = incentivoRepository.findAll();

        return new ResponseEntity(incentivos, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar um incentivo em uma ação ISP")
    @ApiResponse(responseCode = "201", description = "Incentivo cadastrado com sucesso!")
    @PostMapping
    @Transactional
    public ResponseEntity cadastrarIncentivoAcaoISP(@RequestBody Map<String, String> incentivoBody) {
        Incentivo incentivo = new Incentivo(incentivoBody.get("nome"));
        incentivoRepository.save(incentivo);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Excluir um incentivo de uma ação ISP")
    @ApiResponse(responseCode = "200", description = "Incentivo excluído com sucesso!")
    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity excluirIncentivoAcaoISP(@PathVariable("id") Integer incentivoID) {
        incentivoRepository.deleteById(incentivoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
