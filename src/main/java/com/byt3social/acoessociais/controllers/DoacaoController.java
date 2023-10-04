package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.services.DoacaoService;
import com.byt3social.acoessociais.services.PagseguroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class DoacaoController {
    @Autowired
    private PagseguroService pagseguroService;
    @Autowired
    private DoacaoService doacaoService;

    @CrossOrigin
    @GetMapping("/doacoes/session")
    public ResponseEntity gerarSessaoDoador() {
        String sessaoID = pagseguroService.gerarIDSessaoDoador();

        return new ResponseEntity(sessaoID, HttpStatus.OK);
    }

    @CrossOrigin
    @PostMapping("/doacoes")
    public ResponseEntity realizarDoacao(@RequestBody DoacaoDTO doacaoDTO) {
        doacaoService.realizarDoacao(doacaoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @CrossOrigin
    @PostMapping("/doacoes/pagseguro")
    public ResponseEntity atualizarStatusDoacao(@RequestParam("notificationCode") String notificationCode) {
        doacaoService.processarNotificacao(notificationCode);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/doacoes/{id}/cancelamentos")
    public ResponseEntity cancelarDoacao(@PathVariable("id") Integer doacaoID) {
        doacaoService.cancelarDoacao(doacaoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/doacoes/{id}/estornos")
    public ResponseEntity estornarDoacao(@PathVariable("id") Integer doacaoID) {
        doacaoService.estornarDoacao(doacaoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
