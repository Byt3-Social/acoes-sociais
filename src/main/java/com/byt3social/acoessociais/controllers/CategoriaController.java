package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Categoria;
import com.byt3social.acoessociais.repositories.CategoriaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class CategoriaController {
    @Autowired
    private CategoriaRepository categoriaRepository;

    @GetMapping("/categorias")
    public ResponseEntity consultarCategoriasAcaoISP() {
        List<Categoria> categorias = categoriaRepository.findAll();

        return new ResponseEntity(categorias, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/categorias")
    public ResponseEntity cadastrarCategoriaAcaoISP(@RequestBody Map<String, String> categoriaBody) {
        Categoria categoria = new Categoria(categoriaBody.get("nome"));
        categoriaRepository.save(categoria);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/categorias/{id}")
    public ResponseEntity excluirCategoriaAcaoISP(@PathVariable("id") Integer categoriaID) {
        categoriaRepository.deleteById(categoriaID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
