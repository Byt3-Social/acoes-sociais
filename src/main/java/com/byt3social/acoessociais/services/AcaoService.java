package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.OrganizacaoDTO;
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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class AcaoService {
    @Value("${com.byt3social.app.prospeccao.buscar-organizacao-url}")
    private String buscarOrganizacaoUrl;
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
    @Autowired
    private PDSignService pdSignService;

    @Transactional
    public Object salvarArquivo(Integer acaoID, String tipoAcao, String upload, MultipartFile arquivo, String token) {
        Acao acao = null;

        if(tipoAcao.equals("isp")) {
            acao = acaoISPRepository.findById(acaoID).get();
        } else if(tipoAcao.equals("voluntariado")) {
            acao = acaoVoluntariadoRepository.findById(acaoID).get();
        } else {
            throw new RuntimeException();
        }

        switch (upload) {
            case "documento" -> {
                return salvarDocumento(arquivo, acao);
            }
            case "contrato" -> {
                return salvarContrato(arquivo, acao, token);
            }
            default -> throw new InvalidOperationTypeException();
        }
    }

    @Transactional
    public Arquivo salvarDocumento(MultipartFile documento, Acao acao) {
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

        return arquivoAcao;
    }

    @Transactional
    public Contrato salvarContrato(MultipartFile contrato, Acao acao, String token) {
        if(!FilenameUtils.isExtension(contrato.getOriginalFilename(), "pdf")) {
            throw new FileTypeNotSupportedException();
        }

        String nomeContrato;
        String pastaContrato;
        Integer organizacaoId;

        if(acao instanceof AcaoVoluntariado) {
            nomeContrato = ((AcaoVoluntariado) acao).getNomeAcao() + " - Contrato." + FilenameUtils.getExtension(contrato.getOriginalFilename());
            pastaContrato = "acoes/voluntariado/" + ((AcaoVoluntariado) acao).getId() + "/contratos/";

            organizacaoId = ((AcaoVoluntariado) acao).getOrganizacaoId();
        } else {
            nomeContrato = ((AcaoISP) acao).getNomeAcao() + " - Contrato." + FilenameUtils.getExtension(contrato.getOriginalFilename());
            pastaContrato = "acoes/isp/" + ((AcaoISP) acao).getId() + "/contratos/";

            organizacaoId = ((AcaoISP) acao).getOrganizacaoId();
        }

        String caminhoContrato = pastaContrato + nomeContrato;

        if(!amazonS3Service.existeObjeto(pastaContrato)) {
            amazonS3Service.criarPasta(pastaContrato);
        }

        amazonS3Service.armazenarArquivo(contrato, caminhoContrato);

        OrganizacaoDTO organizacaoDTO = buscarOrganizacao(organizacaoId, token);

        String processoPdSignId = pdSignService.criarProcesso(organizacaoDTO.responsavel());
        String documentoPdSignId = pdSignService.criarDocumento(processoPdSignId, "Contrato");

        Contrato novoContrato = new Contrato(caminhoContrato, processoPdSignId, documentoPdSignId);
        contratoRepository.save(novoContrato);

        if(acao instanceof AcaoVoluntariado) {
            ((AcaoVoluntariado) acao).incluirContrato(novoContrato);
        } else {
            ((AcaoISP) acao).incluirContrato(novoContrato);
        }

        pdSignService.uploadDocumento(contrato, novoContrato.getPdsignProcessoId(), novoContrato.getPdsignDocumentoId());

        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.schedule(new PDSign(pdSignService, novoContrato, executorService), 5, TimeUnit.SECONDS);

        return novoContrato;
    }

    private OrganizacaoDTO buscarOrganizacao(Integer organizacaoId, String token) {
        RestTemplate restTemplateOrganizacao = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(token.replace("Bearer ", ""));
        headers.set("B3Social-User", "colaborador");

        HttpEntity<Void> requestEntity = new HttpEntity<>(headers);

        return (restTemplateOrganizacao.exchange(buscarOrganizacaoUrl + organizacaoId, HttpMethod.GET, requestEntity, OrganizacaoDTO.class)).getBody();
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

    @Transactional
    public void excluirArquivo(Integer arquivoID, String tipo) {
        switch (tipo) {
            case "documento" -> {
                Arquivo arquivo = arquivoRepository.findById(arquivoID).get();
                amazonS3Service.excluirArquivo(arquivo.getCaminhoS3());
                arquivoRepository.deleteById(arquivoID);
            }

            case "contrato" -> {
                Contrato contrato = contratoRepository.findById(arquivoID).get();

                if(contrato.getAcaoVoluntariado() != null) {
                    AcaoVoluntariado acao = contrato.getAcaoVoluntariado();
                    acao.excluirContrato();
                } else {
                    AcaoISP acao = contrato.getAcaoISP();
                    acao.excluirContrato();
                }

                amazonS3Service.excluirArquivo(contrato.getCaminhoS3());
                contratoRepository.deleteById(arquivoID);
            }

            default -> throw new InvalidOperationTypeException();
        }
    }

    public List<AcaoISP> buscarAcoes(Integer organizacaoId) {
        List<AcaoISP> acoesISP = acaoISPRepository.findByOrganizacaoId(organizacaoId);

        return acoesISP;
    }

    public List<Acao> buscarAcoes() {
        List<AcaoVoluntariado> acoesVoluntariado = acaoVoluntariadoRepository.findAll();
        List<AcaoISP> acoesISP = acaoISPRepository.findAll();
        List<Acao> acoes = new ArrayList<>();
        acoes.addAll(acoesVoluntariado);
        acoes.addAll(acoesISP);

        return acoes;
    }

    public class PDSign implements Runnable {
        private Contrato contrato;
        private PDSignService pdSignService;
        private ScheduledExecutorService executorService;

        public PDSign(PDSignService pdSignService, Contrato contrato, ScheduledExecutorService executorService) {
            this.contrato = contrato;
            this.pdSignService = pdSignService;
            this.executorService = executorService;
        }

        @Override
        public void run() {
            pdSignService.updateProcesso(contrato.getPdsignProcessoId());
        }
    }
}
