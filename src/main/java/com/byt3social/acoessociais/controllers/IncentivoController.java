package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Incentivo;
import com.byt3social.acoessociais.repositories.IncentivoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class IncentivoController {
    @Autowired
    private IncentivoRepository incentivoRepository;

    @GetMapping("/incentivos")
    public ResponseEntity consultarIncentivosAcaoISP() {
        List<Incentivo> incentivos = incentivoRepository.findAll();

        return new ResponseEntity(incentivos, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/incentivos")
    public ResponseEntity cadastrarIncentivoAcaoISP(@RequestBody Map<String, String> incentivoBody) {
        Incentivo incentivo = new Incentivo(incentivoBody.get("nome"));
        incentivoRepository.save(incentivo);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/incentivos/{id}")
    public ResponseEntity excluirIncentivoAcaoISP(@PathVariable("id") Integer incentivoID) {
        incentivoRepository.deleteById(incentivoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
