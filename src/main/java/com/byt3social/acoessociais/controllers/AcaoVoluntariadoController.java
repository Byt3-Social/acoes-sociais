package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.services.AcaoVoluntariadoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AcaoVoluntariadoController {
    @Autowired
    private AcaoVoluntariadoService acaoVoluntariadoService;

    @PostMapping( "/acoes-voluntariado")
    public ResponseEntity cadastrarAcaoVoluntariado(@Valid @RequestBody AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        acaoVoluntariadoService.cadastrarAcaoVoluntariado(acaoVoluntariadoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @PutMapping( "/acoes-voluntariado/{id}")
    public ResponseEntity atualizarAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @Valid @RequestBody AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        acaoVoluntariadoService.atualizarAcaoVoluntariado(acaoVoluntariadoID, acaoVoluntariadoDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/acoes-voluntariado")
    public ResponseEntity consultarAcoesVoluntariado() {
        List<AcaoVoluntariado> acoesVoluntariado = acaoVoluntariadoService.consultarAcoesVoluntariado();

        return new ResponseEntity(acoesVoluntariado, HttpStatus.OK);
    }

    @GetMapping("/acoes-voluntariado/{id}")
    public ResponseEntity consultarAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoService.consultarAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(acaoVoluntariado, HttpStatus.OK);
    }

    @DeleteMapping("/acoes-voluntariado/{id}")
    public ResponseEntity excluirAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        acaoVoluntariadoService.excluirAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
