package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.models.*;
import com.byt3social.acoessociais.repositories.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;

@Service
public class UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private SegmentoRepository segmentoRepository;
    @Autowired
    private InteresseRepository interesseRepository;
    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;
    @Autowired
    private InscricaoRepository inscricaoRepository;
    @Autowired
    private EmailService emailService;

    @Transactional
    public void manisfestarInteresse(Integer usuarioID, InteresseDTO interesseDTO) {
        Usuario usuario = usuarioRepository.findById(usuarioID).get();
        ListIterator<Interesse> interesseListIterator = usuario.getInteresses().listIterator();

        while(interesseListIterator.hasNext()) {
            Interesse interesse = interesseListIterator.next();

            Boolean continuaInteressado = interesseDTO.interesses().contains(interesse.getSegmento().getId());

            if(!continuaInteressado) {
                interesseListIterator.remove();
                interesseRepository.delete(interesse);
            }
        }

        for(Integer segmentoID : interesseDTO.interesses()) {
            Boolean interesseVinculado = usuario.getInteresses().stream().anyMatch(interesse -> interesse.getSegmento().getId().equals(segmentoID));

            if(!interesseVinculado) {
                Segmento segmento = segmentoRepository.findById(segmentoID).get();
                Interesse novoInteresse = new Interesse(segmento, usuario);

                interesseRepository.save(novoInteresse);
                interesseListIterator.add(novoInteresse);
            }
        }
    }

    public List<Usuario> consultarUsuarios() {
        return usuarioRepository.findAll();
    }

    public void realizarInscricao(Integer participanteID, Integer acaoID) {
        Usuario participante = usuarioRepository.findById(participanteID).get();
        AcaoVoluntariado acaoVoluntariado = acaoVoluntariadoRepository.findById(acaoID).get();
        Inscricao inscricao = new Inscricao(participante, acaoVoluntariado);

        inscricaoRepository.save(inscricao);

        emailService.notificarInscricaoConfirmada(inscricao);
    }

    @Transactional
    public void cancelarInscricao(Integer inscricaoID) {
        Inscricao inscricao = inscricaoRepository.findById(inscricaoID).get();
        inscricao.cancelar();
    }
}
