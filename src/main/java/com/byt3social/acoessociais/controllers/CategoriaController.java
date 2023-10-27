package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Categoria;
import com.byt3social.acoessociais.repositories.CategoriaRepository;

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
@RequestMapping("/categorias")
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @Operation(summary = "Consultar todas as categorias de ações ISP")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Categoria.class)))
    @GetMapping
    public ResponseEntity consultarCategoriasAcaoISP() {
        List<Categoria> categorias = categoriaRepository.findAll();

        return new ResponseEntity(categorias, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar uma categoria de ações ISP")
    @ApiResponse(responseCode = "201", description = "Categoria cadastrada com sucesso!")
    @Transactional
    @PostMapping
    public ResponseEntity cadastrarCategoriaAcaoISP(@RequestBody Map<String, String> categoriaBody) {
        Categoria categoria = new Categoria(categoriaBody.get("nome"));
        categoriaRepository.save(categoria);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Excluir uma categoria de ações ISP")
    @ApiResponse(responseCode = "200", description = "Categoria excluída com sucesso!")
    @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    @Transactional
    @DeleteMapping("/{id}")
    public ResponseEntity excluirCategoriaAcaoISP(@PathVariable("id") Integer categoriaID) {
        categoriaRepository.deleteById(categoriaID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
