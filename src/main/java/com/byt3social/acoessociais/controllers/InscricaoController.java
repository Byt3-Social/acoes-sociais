package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InscricaoDTO;
import com.byt3social.acoessociais.services.InscricaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InscricaoController {
    @Autowired
    private InscricaoService inscricaoService;

    @PostMapping("/inscricoes")
    public ResponseEntity realizarInscricao(@RequestBody InscricaoDTO inscricaoDTO) {
        inscricaoService.realizarInscricao(inscricaoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/inscricoes/{inscricao}")
    public ResponseEntity cancelarInscricao(@PathVariable("inscricao") Integer inscricaoID) {
        inscricaoService.cancelarInscricao(inscricaoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
