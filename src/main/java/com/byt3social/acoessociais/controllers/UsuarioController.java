package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.models.Usuario;
import com.byt3social.acoessociais.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class UsuarioController {
    @Autowired
    private UsuarioService usuarioService;

    @PostMapping("/usuarios/{participante}/inscricoes/{acao}")
    public ResponseEntity realizarInscricao(@PathVariable("participante") Integer participanteID, @PathVariable("acao") Integer acaoID) {
        usuarioService.realizarInscricao(participanteID, acaoID);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @DeleteMapping("/usuarios/inscricoes/{inscricao}")
    public ResponseEntity cancelarInscricao(@PathVariable("inscricao") Integer inscricaoID) {
        usuarioService.cancelarInscricao(inscricaoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/usuarios/{id}/interesses")
    public ResponseEntity manifestarInteresse(@PathVariable("id") Integer usuarioID, @RequestBody InteresseDTO interesseDTO) {
        usuarioService.manisfestarInteresse(usuarioID, interesseDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/usuarios")
    public ResponseEntity listarUsuarios() {
        List<Usuario> usuarios = usuarioService.consultarUsuarios();

        return new ResponseEntity(usuarios, HttpStatus.OK);
    }
}
