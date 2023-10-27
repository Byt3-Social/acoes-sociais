package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.dto.OpcaoContribuicaoDTO;
import com.byt3social.acoessociais.dto.PDSignProcessoDTO;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import com.byt3social.acoessociais.services.AcaoVoluntariadoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
public class AcaoVoluntariadoController {
    @Autowired
    private AcaoVoluntariadoService acaoVoluntariadoService;

    @Operation(summary = "Cadastrar uma ação de voluntariado")
    @ApiResponse(responseCode = "201", description = "Ação de voluntariado cadastrada com sucesso!")
    @PostMapping( "/acoes-voluntariado")
    public ResponseEntity cadastrarAcaoVoluntariado(@Valid @RequestBody AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        Integer id = acaoVoluntariadoService.cadastrarAcaoVoluntariado(acaoVoluntariadoDTO);

        return new ResponseEntity(id, HttpStatus.CREATED);
    }

    @Operation(summary = "Atualizar uma ação de voluntariado")
    @ApiResponse(responseCode = "204", description = "Ação de voluntariado atualizada com sucesso!")
    @ApiResponse(responseCode = "404", description = "Ação de voluntariado não encontrada")
    @PutMapping( "/acoes-voluntariado/{id}")
    public ResponseEntity atualizarAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @Valid @RequestBody AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        acaoVoluntariadoService.atualizarAcaoVoluntariado(acaoVoluntariadoID, acaoVoluntariadoDTO);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @Operation(summary = "Consultar todas as ações de voluntariado")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = AcaoVoluntariado.class)))
    @GetMapping("/acoes-voluntariado")
    public ResponseEntity consultarAcoesVoluntariado() {
        List<AcaoVoluntariado> acoesVoluntariado = acaoVoluntariadoService.consultarAcoesVoluntariado();

        return new ResponseEntity(acoesVoluntariado, HttpStatus.OK);
    }

    @Operation(summary = "Consultar uma ação de voluntariado específica")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = AcaoVoluntariado.class)))
    @ApiResponse(responseCode = "404", description = "Ação de voluntariado não encontrada")
    @GetMapping("/acoes-voluntariado/{id}")
    public ResponseEntity consultarAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoService.consultarAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(acaoVoluntariado, HttpStatus.OK);
    }

    @Operation(summary = "Consultar inscrições em uma ação de voluntariado")
    @ApiResponse(responseCode = "200", description = "Consulta realizada com sucesso!", content = @Content(schema = @Schema(implementation = Inscricao.class)))
    @GetMapping("/acoes-voluntariado/{id}/inscricoes")
    public ResponseEntity consultarInscricoesAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        List<Inscricao> inscricoes = acaoVoluntariadoService.consultarInscricoesAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(inscricoes, HttpStatus.OK);
    }

    @Operation(summary = "Excluir uma ação de voluntariado")
    @ApiResponse(responseCode = "200", description = "Ação de voluntariado excluída com sucesso!")
    @ApiResponse(responseCode = "404", description = "Ação de voluntariado não encontrada")
    @DeleteMapping("/acoes-voluntariado/{id}")
    public ResponseEntity excluirAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        acaoVoluntariadoService.excluirAcaoVoluntariado(acaoVoluntariadoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Salvar uma imagem em uma ação de voluntariado")
    @ApiResponse(responseCode = "200", description = "Imagem salva com sucesso!")
    @ApiResponse(responseCode = "400", description = "Cadastro mal-sucedido, verifique os dados")
    @PostMapping("/acoes-voluntariado/{id}/imagens")
    public ResponseEntity salvarArquivoAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @RequestBody MultipartFile imagem) {
        String urlImagem = acaoVoluntariadoService.salvarImagem(acaoVoluntariadoID, imagem);

        return new ResponseEntity(urlImagem, HttpStatus.OK);
    }

    @Operation(summary = "Excluir uma imagem de uma ação de voluntariado")
    @ApiResponse(responseCode = "200", description = "Imagem excluída com sucesso!")
    @ApiResponse(responseCode = "404", description = "Imagem não encontrada")
    @DeleteMapping("/acoes-voluntariado/{id}/imagens")
    public ResponseEntity excluirArquivoAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID) {
        acaoVoluntariadoService.excluirImagem(acaoVoluntariadoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Adicionar opção de contribuição a uma ação de voluntariado")
    @ApiResponse(responseCode = "201", description = "Opção de contribuição adicionada com sucesso!")
    @PostMapping("/acoes-voluntariado/{id}/opcoes-contribuicao")
    public ResponseEntity adicionarOpcaoContribuicaoAcaoVoluntariado(@PathVariable("id") Integer acaoVoluntariadoID, @RequestBody OpcaoContribuicaoDTO opcaoContribuicaoDTO) {
        acaoVoluntariadoService.adicionarOpcaoContribuicao(acaoVoluntariadoID, opcaoContribuicaoDTO);

        return new ResponseEntity(HttpStatus.CREATED);
    }

    @Operation(summary = "Excluir opção de contribuição de uma ação de voluntariado")
    @ApiResponse(responseCode = "200", description = "Opção de contribuição excluída com sucesso!")
    @ApiResponse(responseCode = "404", description = "Opção de contribuição não encontrada")
    @DeleteMapping("/acoes-voluntariado/opcoes-contribuicao/{id}")
    public ResponseEntity excluirOpcaoContribuicaoAcaoVoluntariado(@PathVariable("id") Integer opcaoContribuicaoID) {
        acaoVoluntariadoService.excluirOpcaoContribuicao(opcaoContribuicaoID);

        return new ResponseEntity(HttpStatus.OK);
    }

    @Operation(summary = "Buscar ações para divulgação")
    @ApiResponse(responseCode = "200", description = "Ações para divulgação recuperadas com sucesso!", content = @Content(schema = @Schema(implementation = Map.class)))
    @GetMapping("/acoes/divulgacao")
    public ResponseEntity buscarAcoesParaDivulgacao() {
        Map<String, List<AcaoVoluntariado>> acoes = acaoVoluntariadoService.buscarAcoesParaDivulgacao();

        return new ResponseEntity(acoes, HttpStatus.OK);
    }

    @Operation(summary = "Consultar processos PDSign de uma ação de voluntariado")
    @ApiResponse(responseCode = "200", description = "Processos PDSign consultados com sucesso!", content = @Content(schema = @Schema(implementation = List.class)))
    @GetMapping("/acao-voluntariado/{id}/pdsign")
    public ResponseEntity consultarProcessoPDSign(@PathVariable("id") Integer acaoId) {
        List<PDSignProcessoDTO> pdSignProcessoDTO = acaoVoluntariadoService.consultarProcessoPDSign(acaoId);

        return new ResponseEntity<>(pdSignProcessoDTO, HttpStatus.OK);
    }
}
