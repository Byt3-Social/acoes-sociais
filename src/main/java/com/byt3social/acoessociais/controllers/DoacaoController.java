package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.PagseguroTransacaoDTO;
import com.byt3social.acoessociais.models.Doacao;
import com.byt3social.acoessociais.services.DoacaoService;

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
import java.util.Map;

@RestController
@RequestMapping("/doacoes")
public class DoacaoController {
    @Autowired
    private DoacaoService doacaoService;

    @Operation(summary = "Realizar uma doação")
    @ApiResponse(responseCode = "201", description = "Doação realizada com sucesso!", content = @Content(schema = @Schema(implementation = Doacao.class)))
    @PostMapping
    public ResponseEntity realizarDoacao(@RequestHeader("B3Social-Colaborador") String colaboradorId, @Valid @RequestBody DoacaoDTO doacaoDTO) {
        Doacao doacao = doacaoService.realizarDoacao(doacaoDTO, Integer.valueOf(colaboradorId));

        return new ResponseEntity(doacao, HttpStatus.CREATED);
    }

    @Operation(summary = "Consultar doações de um colaborador")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping
    public ResponseEntity consultarDoacoes(@RequestHeader("B3Social-Colaborador") String colaboradorId) {
        List<Map> doacoes = doacaoService.consultarDoacoes(Integer.valueOf(colaboradorId));

        return new ResponseEntity(doacoes, HttpStatus.OK);
    }

    @Operation(summary = "Consultar uma doação específica")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Doacao.class)))
    @ApiResponse(responseCode = "404", description = "Doação não encontrada")
    @GetMapping("/{id}")
    public ResponseEntity consultarDoacao(@PathVariable("id") Integer doacaoID) {
        Doacao doacao = doacaoService.consultarDoacao(doacaoID);

        return new ResponseEntity(doacao, HttpStatus.OK);
    }

    @Operation(summary = "Processar notificação de pagamento do PagSeguro")
    @ApiResponse(responseCode = "200", description = "Notificação processada com sucesso!")
    @CrossOrigin
    @PostMapping("/pagseguro")
    public ResponseEntity atualizarStatusDoacao(@RequestBody PagseguroTransacaoDTO pagseguroTransacaoDTO) {
        doacaoService.processarNotificacao(pagseguroTransacaoDTO);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Operation(summary = "Cancelar uma doação")
    @ApiResponse(responseCode = "204", description = "Doação cancelada com sucesso!")
    @ApiResponse(responseCode = "404", description = "Doação não encontrada")
    @PostMapping("/{id}/cancelamentos")
    public ResponseEntity cancelarDoacao(@PathVariable("id") Integer doacaoID) {
        doacaoService.cancelarDoacao(doacaoID);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Gerar estatísticas de uma doação")
    @ApiResponse(responseCode = "200", description = "Estatísticas geradas com sucesso!", content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/{id}/estatisticas")
    public ResponseEntity estatisticas(@PathVariable("id") Integer acaoID) {
        Map<String, Object> estatisticas = doacaoService.gerarEstatisticas(acaoID);

        return new ResponseEntity<>(estatisticas, HttpStatus.OK);
    }
}
