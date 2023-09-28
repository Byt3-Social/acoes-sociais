package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Organizacao;
import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.models.Usuario;
import com.byt3social.acoessociais.repositories.AcaoVoluntariadoRepository;
import com.byt3social.acoessociais.repositories.OrganizacaoRepository;
import com.byt3social.acoessociais.repositories.SegmentoRepository;
import com.byt3social.acoessociais.repositories.UsuarioRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AcaoVoluntariadoService {
    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SegmentoRepository segmentoRepository;
    @Autowired
    private OrganizacaoRepository organizacaoRepository;

    @Transactional
    public void cadastrarAcaoVoluntariado(AcaoVoluntariadoDTO acaoVoluntariadoDTO) {
        Usuario usuario = usuarioRepository.findById(acaoVoluntariadoDTO.usuarioID()).get();

        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        acaoVoluntariado.vincularUsuario(usuario);

        if(acaoVoluntariadoDTO.segmentoID() != null){
            Segmento segmento = segmentoRepository.findById(acaoVoluntariadoDTO.segmentoID()).get();
            acaoVoluntariado.vincularSegmento(segmento);
        }

        if(acaoVoluntariadoDTO.campanha() != null) {
            acaoVoluntariado.criarCampanha(acaoVoluntariadoDTO.campanha());
        }

        if(acaoVoluntariadoDTO.organizacaoID() != null) {
            Organizacao organizacao = organizacaoRepository.findById(acaoVoluntariadoDTO.organizacaoID()).get();

            acaoVoluntariado.vincularOrganizacao(organizacao);
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

        if(acaoVoluntariadoDTO.organizacaoID() != null) {
            Organizacao organizacao = organizacaoRepository.findById(acaoVoluntariadoDTO.organizacaoID()).get();
            acaoVoluntariado.atualizarOrganizacao(organizacao);
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
}
