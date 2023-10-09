package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Area;
import com.byt3social.acoessociais.repositories.AreaRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
public class AreaController {
    @Autowired
    private AreaRepository areaRepository;

    @GetMapping("/areas")
    public ResponseEntity consultarAreasAcaoISP() {
        List<Area> areas = areaRepository.findAll();

        return new ResponseEntity(areas, HttpStatus.OK);
    }

    @Transactional
    @PostMapping("/areas")
    public ResponseEntity cadastrarAreaAcaoISP(@RequestBody Map<String, String> areaBody) {
        Area area = new Area(areaBody.get("nome"));
        areaRepository.save(area);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Transactional
    @DeleteMapping("/areas/{id}")
    public ResponseEntity excluirAreaAcaoISP(@PathVariable("id") Integer areaID) {
        areaRepository.deleteById(areaID);

        return new ResponseEntity(HttpStatus.OK);
    }
}
