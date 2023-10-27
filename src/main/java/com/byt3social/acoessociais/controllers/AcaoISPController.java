package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.dto.PDSignProcessoDTO;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.services.AcaoISPService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
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

    @Operation(summary = "Consultar todas as ações ISP")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/acoes-isp")
    public ResponseEntity consultarAcoesISP() {
        List<AcaoISP> acoesISP = acaoISPService.consultarAcoesISP();

        return new ResponseEntity(acoesISP, HttpStatus.OK);
    }

    @Operation(summary = "Consultar uma ação ISP por ID")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = AcaoISP.class)))
    @ApiResponse(responseCode = "404", description = "Ação ISP não encontrada")
    @GetMapping("/acoes-isp/{id}")
    public ResponseEntity consultarAcaoISP(@PathVariable("id") Integer acaoISPID) {
        AcaoISP acoesISP = acaoISPService.consultarAcaoISP(acaoISPID);

        return new ResponseEntity(acoesISP, HttpStatus.OK);
    }

    @Operation(summary = "Cadastrar uma nova ação ISP")
    @ApiResponse(responseCode = "201", description = "Ação ISP cadastrada com sucesso!", content = @Content(schema = @Schema(implementation = Integer.class)))
    @PostMapping("/acoes-isp")
    public ResponseEntity cadastrarAcaoISP(@Valid @RequestBody AcaoISPDTO acaoISPDTO) {
        Integer id = acaoISPService.cadastrarAcaoISP(acaoISPDTO);

        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar uma ação ISP por ID")
    @ApiResponse(responseCode = "204", description = "Ação ISP atualizada com sucesso")
    @ApiResponse(responseCode = "404", description = "Ação ISP não encontrada")
    @PutMapping("/acoes-isp/{id}")
    public ResponseEntity atualizarAcaoISP(@PathVariable("id") Integer acaoISPID, @Valid @RequestBody AcaoISPDTO acaoISPDTO) {
        acaoISPService.atualizarAcaoISP(acaoISPID, acaoISPDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Excluir uma ação ISP por ID")
    @ApiResponse(responseCode = "200", description = "Ação ISP excluída com sucesso")
    @ApiResponse(responseCode = "404", description = "Ação ISP não encontrada")
    @DeleteMapping("/acoes-isp/{id}")
    public ResponseEntity excluirAcaoISP(@PathVariable("id") Integer acaoISPID) {
        acaoISPService.excluirAcaoISP(acaoISPID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Consultar processos PDSign de uma ação ISP por ID")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/acao-isp/{id}/pdsign")
    public ResponseEntity consultarProcessoPDSign(@PathVariable("id") Integer acaoId) {
        List<PDSignProcessoDTO> pdSignProcessoDTO = acaoISPService.consultarProcessoPDSign(acaoId);

        return new ResponseEntity<>(pdSignProcessoDTO, HttpStatus.OK);
    }
}
