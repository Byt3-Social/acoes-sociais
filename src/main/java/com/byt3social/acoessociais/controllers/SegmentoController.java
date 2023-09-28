package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.SegmentoDTO;
import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.repositories.SegmentoRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class SegmentoController {
    @Autowired
    private SegmentoRepository segmentoRepository;

    @PostMapping("/segmentos")
    @Transactional
    public ResponseEntity cadastrarSegmento(@Valid @RequestBody SegmentoDTO segmentoDTO) {
        Segmento segmento = new Segmento(segmentoDTO);
        segmentoRepository.save(segmento);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @GetMapping("/segmentos")
    public ResponseEntity consultarSegmentos() {
        List<Segmento> segmentos = segmentoRepository.findAll();

        return new ResponseEntity(segmentos, HttpStatus.OK);
    }

    @GetMapping("/segmentos/{id}")
    public ResponseEntity consultarSegmento(@PathVariable("id") Integer segmentoID) {
        Segmento segmento = segmentoRepository.findById(segmentoID).get();

        return new ResponseEntity(segmento, HttpStatus.OK);
    }

    @DeleteMapping("/segmentos/{id}")
    public ResponseEntity excluirSegmento(@PathVariable("id") Integer segmentoID) {
        segmentoRepository.deleteById(segmentoID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
