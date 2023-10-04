package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.dto.OpcaoContribuicaoDTO;
import com.byt3social.acoessociais.dto.OrganizacaoDTO;
import com.byt3social.acoessociais.exceptions.FileTypeNotSupportedException;
import com.byt3social.acoessociais.exceptions.InvalidOperationTypeException;
import com.byt3social.acoessociais.models.*;
import com.byt3social.acoessociais.repositories.*;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
public class AcaoVoluntariadoService {
    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;
    @Autowired
    private SegmentoRepository segmentoRepository;
    @Autowired
    private InscricaoRepository inscricaoRepository;
    @Autowired
    private OpcaoContribuicaoRepository opcaoContribuicaoRepository;
    @Autowired
    private ArquivoRepository arquivoRepository;
    @Autowired
    private ContratoRepository contratoRepository;
    @Autowired
    private AmazonS3Service amazonS3Service;

    @Transactional
    public void cadastrarAcaoVoluntariado(AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);

        if(acaoVoluntariadoDTO.segmentoID() != null){
            Segmento segmento = segmentoRepository.findById(acaoVoluntariadoDTO.segmentoID()).get();
            acaoVoluntariado.vincularSegmento(segmento);
        }

        acaoVoluntariadoRepository.save(acaoVoluntariado);
    }

    @Transactional
    public void atualizarAcaoVoluntariado(Integer acaoVoluntariadoID, AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        AcaoVoluntariado  acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoVoluntariadoID).get();

        acaoVoluntariado.atualizar(acaoVoluntariadoDTO);

        if(acaoVoluntariadoDTO.segmentoID() != null) {
            Segmento segmento = segmentoRepository.findById(acaoVoluntariadoDTO.segmentoID()).get();
            acaoVoluntariado.atualizarSegmento(segmento);
        }
    }

    public List<AcaoVoluntariado> consultarAcoesVoluntariado() {
        return acaoVoluntariadoRepository.findAll();
    }

    public AcaoVoluntariado consultarAcaoVoluntariado(Integer acaoVoluntariadoID) {
        return acaoVoluntariadoRepository.findById(acaoVoluntariadoID).get();
    }

    public void excluirAcaoVoluntariado(Integer acaoVoluntariadoID) {
        acaoVoluntariadoRepository.deleteById(acaoVoluntariadoID);
    }

    public List<Inscricao> consultarInscricoesAcaoVoluntariado(Integer acaoVoluntariadoID) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.getReferenceById(acaoVoluntariadoID);

        return inscricaoRepository.findByAcaoVoluntariado(acaoVoluntariado);
    }

    @Transactional
    public void salvarArquivoAcaoVoluntariado(Integer acaoVoluntariadoID, String tipoArquivo, MultipartFile arquivo) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoVoluntariadoID).get();

        switch (tipoArquivo) {
            case "imagem" -> salvarImagem(arquivo, acaoVoluntariado);
            case "documento" -> salvarDocumento(arquivo, acaoVoluntariado);
            case "contrato" -> salvarContrato(arquivo, acaoVoluntariado);
            default -> throw new InvalidOperationTypeException();
        }
    }

    public String recuperarArquivoAcaoVoluntariado(Integer arquivoID, String tipo) {
        switch (tipo) {
            case "imagem" -> {
                AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(arquivoID).get();
                String caminhoArquivo = acaoVoluntariado.getImagem();

                return amazonS3Service.recuperarArquivo(caminhoArquivo);
            }

            case "documento" -> {
                Arquivo arquivo = arquivoRepository.findById(arquivoID).get();
                String caminhoArquivo = arquivo.getCaminhoS3();

                return amazonS3Service.recuperarArquivo(caminhoArquivo);
            }

            case "contrato" -> {
                Contrato contrato = contratoRepository.findById(arquivoID).get();
                String caminhoContrato = contrato.getCaminhoS3();
                return amazonS3Service.recuperarArquivo(caminhoContrato);
            }

            default -> throw new InvalidOperationTypeException();
        }
    }

    @Transactional
    public void excluirArquivoAcaoVoluntariado(Integer arquivoID, String tipo) {
        switch (tipo) {
            case "imagem" -> {
                AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(arquivoID).get();
                amazonS3Service.excluirArquivo(acaoVoluntariado.getImagem());
                acaoVoluntariado.excluirImagem();
            }

            case "documento" -> {
                Arquivo arquivo = arquivoRepository.findById(arquivoID).get();
                amazonS3Service.excluirArquivo(arquivo.getCaminhoS3());
                arquivoRepository.deleteById(arquivoID);
            }

            case "contrato" -> {
                Contrato contrato = contratoRepository.findById(arquivoID).get();
                amazonS3Service.excluirArquivo(contrato.getCaminhoS3());
                contratoRepository.deleteById(arquivoID);
            }

            default -> throw new InvalidOperationTypeException();
        }
    }

    private void salvarContrato(MultipartFile contrato, AcaoVoluntariado acaoVoluntariado) {
        if(!FilenameUtils.isExtension(contrato.getOriginalFilename(), "pdf")) {
            throw new FileTypeNotSupportedException();
        }

        RestTemplate restTemplate = new RestTemplate();
        OrganizacaoDTO organizacaoDTO = restTemplate.getForObject("http://localhost:8081/organizacoes/" + acaoVoluntariado.getOrganizacaoId(), OrganizacaoDTO.class);

        String nomeContrato = organizacaoDTO.cnpj() + " - Contrato de Doação." + FilenameUtils.getExtension(contrato.getOriginalFilename());
        String pastaContrato = "acoes/voluntariado/" + acaoVoluntariado.getId() + "/contratos/";
        String caminhoContrato = pastaContrato + nomeContrato;

        if(!amazonS3Service.existeObjeto(pastaContrato)) {
            amazonS3Service.criarPasta(pastaContrato);
        }

        amazonS3Service.armazenarArquivo(contrato, caminhoContrato);

        Contrato novoContrato = new Contrato(caminhoContrato);
        contratoRepository.save(novoContrato);
    }

    private void salvarDocumento(MultipartFile documento, AcaoVoluntariado acaoVoluntariado) {
        String nomeDocumento = documento.getOriginalFilename();
        String pastaDocumento = "acoes/voluntariado/" + acaoVoluntariado.getId() + "/arquivos/";
        String caminhoDocumento = pastaDocumento + nomeDocumento;

        if(!amazonS3Service.existeObjeto(pastaDocumento)) {
            amazonS3Service.criarPasta(pastaDocumento);
        }

        amazonS3Service.armazenarArquivo(documento, caminhoDocumento);

        Arquivo arquivoAcaoVoluntariado = new Arquivo(caminhoDocumento, acaoVoluntariado);
        arquivoRepository.save(arquivoAcaoVoluntariado);
    }

    private void salvarImagem(MultipartFile imagem, AcaoVoluntariado acaoVoluntariado) {
        String[] extensoes = {"png", "jpeg", "jpg"};

        if(!FilenameUtils.isExtension(imagem.getOriginalFilename(), extensoes)) {
            throw new FileTypeNotSupportedException();
        }

        String nomeImagem = imagem.getOriginalFilename();
        String pastaImagem = "acoes/voluntariado/" + acaoVoluntariado.getId() + "/imagens/";
        String caminhoImagem = pastaImagem + nomeImagem;

        if(!amazonS3Service.existeObjeto(pastaImagem)) {
            amazonS3Service.criarPasta(pastaImagem);
        }

        amazonS3Service.armazenarArquivo(imagem, caminhoImagem);

        acaoVoluntariado.salvarImagem(caminhoImagem);
    }

    @Transactional
    public void adicionarOpcaoContribuicao(Integer acaoVoluntariadoID, OpcaoContribuicaoDTO opcaoContribuicaoDTO) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoVoluntariadoID).get();
        OpcaoContribuicao opcaoContribuicao = new OpcaoContribuicao(opcaoContribuicaoDTO, acaoVoluntariado);
        opcaoContribuicaoRepository.save(opcaoContribuicao);
    }

    @Transactional
    public void excluirOpcaoContribuicao(Integer opcaoContribuicaoID) {
        opcaoContribuicaoRepository.deleteById(opcaoContribuicaoID);
    }
}
