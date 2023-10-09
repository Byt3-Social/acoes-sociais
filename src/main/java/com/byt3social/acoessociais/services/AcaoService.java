package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.exceptions.FileTypeNotSupportedException;
import com.byt3social.acoessociais.exceptions.InvalidOperationTypeException;
import com.byt3social.acoessociais.models.*;
import com.byt3social.acoessociais.repositories.AcaoISPRepository;
import com.byt3social.acoessociais.repositories.AcaoVoluntariadoRepository;
import com.byt3social.acoessociais.repositories.ArquivoRepository;
import com.byt3social.acoessociais.repositories.ContratoRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class AcaoService {
    @Autowired
    private AmazonS3Service amazonS3Service;
    @Autowired
    private AcaoISPRepository acaoISPRepository;
    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;
    @Autowired
    private ArquivoRepository arquivoRepository;
    @Autowired
    private ContratoRepository contratoRepository;

    @Transactional
    public void salvarArquivo(Integer acaoID, String tipoAcao, String upload, MultipartFile arquivo) {
        Acao acao = null;

        if(tipoAcao.equals("isp")) {
            acao = acaoISPRepository.findById(acaoID).get();
        } else if(tipoAcao.equals("voluntariado")) {
            acao = acaoVoluntariadoRepository.findById(acaoID).get();
        } else {
            throw new RuntimeException();
        }

        switch (upload) {
            case "documento" -> salvarDocumento(arquivo, acao);
            case "contrato" -> salvarContrato(arquivo, acao);
            default -> throw new InvalidOperationTypeException();
        }
    }

    @Transactional
    public void salvarDocumento(MultipartFile documento, Acao acao) {
        String nomeDocumento = documento.getOriginalFilename();
        Long tamanhoDocumento = documento.getSize();
        String pastaDocumento;

        if(acao instanceof AcaoVoluntariado) {
            pastaDocumento = "acoes/voluntariado/" + ((AcaoVoluntariado) acao).getId() + "/documentos/";
        } else {
            pastaDocumento = "acoes/isp/" + ((AcaoISP) acao).getId() + "/documentos/";
        }

        String caminhoDocumento = pastaDocumento + nomeDocumento;

        if(!amazonS3Service.existeObjeto(pastaDocumento)) {
            amazonS3Service.criarPasta(pastaDocumento);
        }

        amazonS3Service.armazenarArquivo(documento, caminhoDocumento);

        Arquivo arquivoAcao = new Arquivo(caminhoDocumento, nomeDocumento, tamanhoDocumento, acao);
        arquivoRepository.save(arquivoAcao);
    }

    @Transactional
    public void salvarContrato(MultipartFile contrato, Acao acao) {
        if(!FilenameUtils.isExtension(contrato.getOriginalFilename(), "pdf")) {
            throw new FileTypeNotSupportedException();
        }

        String nomeContrato;
        String pastaContrato;

        if(acao instanceof AcaoVoluntariado) {
            nomeContrato = ((AcaoVoluntariado) acao).getNomeAcao() + " - Contrato." + FilenameUtils.getExtension(contrato.getOriginalFilename());
            pastaContrato = "acoes/voluntariado/" + ((AcaoVoluntariado) acao).getId() + "/contratos/";
        } else {
            nomeContrato = ((AcaoISP) acao).getNomeAcao() + " - Contrato." + FilenameUtils.getExtension(contrato.getOriginalFilename());
            pastaContrato = "acoes/isp/" + ((AcaoISP) acao).getId() + "/contratos/";
        }

        String caminhoContrato = pastaContrato + nomeContrato;

        if(!amazonS3Service.existeObjeto(pastaContrato)) {
            amazonS3Service.criarPasta(pastaContrato);
        }

        amazonS3Service.armazenarArquivo(contrato, caminhoContrato);

        Contrato novoContrato = new Contrato(caminhoContrato);
        contratoRepository.save(novoContrato);

        if(acao instanceof AcaoVoluntariado) {
            ((AcaoVoluntariado) acao).incluirContrato(novoContrato);
        } else {
            ((AcaoISP) acao).incluirContrato(novoContrato);
        }
    }

    public String recuperarArquivo(Integer arquivoID, String download) {
        switch (download) {
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

    public void excluirArquivo(Integer arquivoID, String tipo) {
        switch (tipo) {
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
}
