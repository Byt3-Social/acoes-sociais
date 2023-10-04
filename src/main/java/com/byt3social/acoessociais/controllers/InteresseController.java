package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.services.InteresseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class InteresseController {
    @Autowired
    private InteresseService interesseService;

    @PostMapping("/interesses")
    public ResponseEntity manifestarInteresse(@RequestBody InteresseDTO interesseDTO) {
        interesseService.manisfestarInteresse(interesseDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
