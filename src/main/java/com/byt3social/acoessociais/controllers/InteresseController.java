package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.services.InteresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class InteresseController {
    @Autowired
    private InteresseService interesseService;

    @PostMapping("/interesses")
    public ResponseEntity manifestarInteresse(@RequestHeader("B3Social-Colaborador") String colaboradorId, @RequestBody InteresseDTO interesseDTO) {
        interesseService.manisfestarInteresse(interesseDTO, Integer.valueOf(colaboradorId));

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/interesses")
    public ResponseEntity consultarInteresses(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Integer> interesses = interesseService.consultarInteresses(Integer.valueOf(colaboradorId));

        return new ResponseEntity(interesses, HttpStatus.OK);
    }
}
