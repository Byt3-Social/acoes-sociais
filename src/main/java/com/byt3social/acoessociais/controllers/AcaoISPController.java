package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.services.AcaoISPService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AcaoISPController {
    @Autowired
    private AcaoISPService acaoISPService;

    @GetMapping("/acoes-isp")
    public ResponseEntity consultarAcoesISP() {
        List<AcaoISP> acoesISP = acaoISPService.consultarAcoesISP();

        return new ResponseEntity(acoesISP, HttpStatus.OK);
    }

    @GetMapping("/acoes-isp/{id}")
    public ResponseEntity consultarAcaoISP(@PathVariable("id") Integer acaoISPID) {
        AcaoISP acoesISP = acaoISPService.consultarAcaoISP(acaoISPID);

        return new ResponseEntity(acoesISP, HttpStatus.OK);
    }

    @PostMapping("/acoes-isp")
    public ResponseEntity cadastrarAcaoISP(@Valid @RequestBody AcaoISPDTO acaoISPDTO) {
        Integer id = acaoISPService.cadastrarAcaoISP(acaoISPDTO);

        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @PutMapping("/acoes-isp/{id}")
    public ResponseEntity atualizarAcaoISP(@PathVariable("id") Integer acaoISPID, @Valid @RequestBody AcaoISPDTO acaoISPDTO) {
        acaoISPService.atualizarAcaoISP(acaoISPID, acaoISPDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/acoes-isp/{id}")
    public ResponseEntity excluirAcaoISP(@PathVariable("id") Integer acaoISPID) {
        acaoISPService.excluirAcaoISP(acaoISPID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
