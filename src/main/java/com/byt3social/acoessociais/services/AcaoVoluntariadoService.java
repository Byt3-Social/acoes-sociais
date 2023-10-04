package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
}
