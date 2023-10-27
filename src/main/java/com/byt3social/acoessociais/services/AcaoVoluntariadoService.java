package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.dto.OpcaoContribuicaoDTO;
import com.byt3social.acoessociais.dto.PDSignProcessoDTO;
import com.byt3social.acoessociais.dto.PDSignProcessosDTO;
import com.byt3social.acoessociais.exceptions.FileTypeNotSupportedException;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import com.byt3social.acoessociais.models.OpcaoContribuicao;
import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.repositories.*;
import jakarta.transaction.Transactional;
import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

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
    private AmazonS3Service amazonS3Service;
    @Value("${com.byt3social.aws.main-bucket-name}")
    private String nomeBucketPrincipal;
    @Value("${com.byt3social.aws.region}")
    private String awsRegion;
    @Autowired
    private PDSignService pdSignService;

    @Transactional
    public Integer cadastrarAcaoVoluntariado(AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);

        if(acaoVoluntariadoDTO.segmentoID() != null){
            Segmento segmento = segmentoRepository.findById(acaoVoluntariadoDTO.segmentoID()).get();
            acaoVoluntariado.vincularSegmento(segmento);
        }

        acaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);

        return acaoVoluntariado.getId();
    }

    @Transactional
    public void atualizarAcaoVoluntariado(Integer acaoVoluntariadoID, AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        AcaoVoluntariado  acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoVoluntariadoID).get();

        acaoVoluntariado.atualizar(acaoVoluntariadoDTO);

        if(acaoVoluntariadoDTO.segmentoID() != null) {
            Segmento segmento = segmentoRepository.findById(acaoVoluntariadoDTO.segmentoID()).get();
            acaoVoluntariado.atualizarSegmento(segmento);
        }

        List<OpcaoContribuicao> novasOpcoesContribuicao = new ArrayList<>();
        ListIterator<OpcaoContribuicao> opcaoContribuicaoListIterator = acaoVoluntariado.getOpcoesContribuicao().listIterator();

        if(acaoVoluntariadoDTO.opcoesContribuicao() != null) {
            while(opcaoContribuicaoListIterator.hasNext()) {
                OpcaoContribuicao opcaoContribuicao = opcaoContribuicaoListIterator.next();

                Boolean existeOpcaoContribuicao = acaoVoluntariadoDTO.opcoesContribuicao().stream().anyMatch(opcaoContribuicaoDTO -> opcaoContribuicaoDTO.id() == opcaoContribuicao.getId());

                if(!existeOpcaoContribuicao) {
                    opcaoContribuicaoListIterator.remove();
                    opcaoContribuicaoRepository.delete(opcaoContribuicao);
                }
            }

            for(OpcaoContribuicaoDTO opcaoContribuicaoDTO : acaoVoluntariadoDTO.opcoesContribuicao()) {
                Boolean existeOpcaoContribuicao = acaoVoluntariado.getOpcoesContribuicao().stream().anyMatch(opcaoContribuicao -> opcaoContribuicao.getId() == opcaoContribuicaoDTO.id());

                if(!existeOpcaoContribuicao) {
                    OpcaoContribuicao opcaoContribuicao = new OpcaoContribuicao(opcaoContribuicaoDTO, acaoVoluntariado);

                    novasOpcoesContribuicao.add(opcaoContribuicao);
                }
            }

            opcaoContribuicaoRepository.saveAll(novasOpcoesContribuicao);
        }
    }

    public List<AcaoVoluntariado> consultarAcoesVoluntariado() {
        return acaoVoluntariadoRepository.findAll(Sort.by(Sort.Direction.DESC, "createdAt"));
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
    public String salvarImagem(Integer acaoVoluntariadoID, MultipartFile imagem) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoVoluntariadoID).get();

        String[] extensoes = {"png", "jpeg", "jpg"};

        if(!FilenameUtils.isExtension(imagem.getOriginalFilename(), extensoes)) {
            throw new FileTypeNotSupportedException();
        }

        String nomeImagem = imagem.getOriginalFilename();
        String pastaImagem = "acoes/voluntariado/" + acaoVoluntariado.getId() + "/imagens/";
        String caminhoImagem = pastaImagem + nomeImagem;
        String urlDownload = "https://" + nomeBucketPrincipal + ".s3." + awsRegion + ".amazonaws.com/" + caminhoImagem;

        if(!amazonS3Service.existeObjeto(pastaImagem)) {
            amazonS3Service.criarPasta(pastaImagem);
        }

        amazonS3Service.armazenarArquivo(imagem, caminhoImagem);

        if(acaoVoluntariado.getImagem() != null) {
            excluirImagem(acaoVoluntariadoID);
        }

        acaoVoluntariado.salvarImagem(urlDownload);

        return acaoVoluntariado.getImagem();
    }

    @Transactional
    public void excluirImagem(Integer acaoVoluntariadoID) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoVoluntariadoID).get();
        String[] url = acaoVoluntariado.getImagem().split("https://" + nomeBucketPrincipal + ".s3." + awsRegion + ".amazonaws.com/");

        amazonS3Service.excluirArquivo(url[1]);
        acaoVoluntariado.excluirImagem();
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

    public Map<String, List<AcaoVoluntariado>> buscarAcoesParaDivulgacao() {
        List<AcaoVoluntariado> acoesVoluntariado = acaoVoluntariadoRepository.buscarAcoesVoluntariadoEmAndamento();
        List<AcaoVoluntariado> acoesDoacao = acaoVoluntariadoRepository.buscarAcoesDoacaoEmAndamento();

        Map<String, List<AcaoVoluntariado>> acoes = new HashMap<>();
        acoes.put("voluntariado", acoesVoluntariado);
        acoes.put("doacao", acoesDoacao);

        return acoes;
    }

    public List<PDSignProcessoDTO> consultarProcessoPDSign(Integer acaoId) {
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoId).get();

        if(acaoVoluntariado.getContrato() == null) {
            return List.of();
        }

        PDSignProcessosDTO pdSignProcessosDTO = pdSignService.buscarProcessosPDSign();

        List<PDSignProcessoDTO> pdSignProcessoDTOList = new ArrayList<>();

        PDSignProcessoDTO pdSignProcesso = pdSignProcessosDTO.processes().stream().filter(pdSignProcessoDTO -> pdSignProcessoDTO.id().equals(acaoVoluntariado.getContrato().getPdsignProcessoId())).findFirst().get();
        pdSignProcessoDTOList.add(pdSignProcesso);

        return pdSignProcessoDTOList;
    }
}
