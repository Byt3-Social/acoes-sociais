package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.services.DoacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DoacaoController {
    @Autowired
    private DoacaoService doacaoService;

    @CrossOrigin
    @PostMapping("/doacoes")
    public ResponseEntity realizarDoacao(@RequestBody DoacaoDTO doacaoDTO) {
        doacaoService.realizarDoacao(doacaoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/doacoes/pagseguro")
    public ResponseEntity atualizarStatusDoacao(@RequestBody PagseguroTransacaoDTO pagseguroTransacaoDTO) {
        doacaoService.processarNotificacao(pagseguroTransacaoDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/doacoes/{id}/cancelamentos")
    public ResponseEntity cancelarDoacao(@PathVariable("id") Integer doacaoID) {
        doacaoService.cancelarDoacao(doacaoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
