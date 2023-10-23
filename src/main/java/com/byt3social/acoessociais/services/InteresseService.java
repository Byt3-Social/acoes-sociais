package com.byt3social.acoessociais.services;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.models.Interesse;
import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.repositories.InteresseRepository;
import com.byt3social.acoessociais.repositories.SegmentoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ListIterator;

@Service
public class InteresseService {
    @Autowired
    private SegmentoRepository segmentoRepository;
    @Autowired
    private InteresseRepository interesseRepository;

    @Transactional
    public void manisfestarInteresse(InteresseDTO interesseDTO, Integer colaboradorId) {
        List<Interesse> interesses = interesseRepository.findByUsuarioId(colaboradorId);
        ListIterator<Interesse> interesseListIterator = interesses.listIterator();

        while(interesseListIterator.hasNext()) {
            Interesse interesse = interesseListIterator.next();

            Boolean continuaInteressado = interesseDTO.interesses().contains(interesse.getSegmento().getId());

            if(!continuaInteressado) {
                interesseListIterator.remove();
                interesseRepository.delete(interesse);
            }
        }

        for(Integer segmentoID : interesseDTO.interesses()) {
            Boolean existeInteresse = interesses.stream().anyMatch(interesse -> interesse.getSegmento().getId().equals(segmentoID));

            if(!existeInteresse) {
                Segmento segmento = segmentoRepository.findById(segmentoID).get();
                Interesse novoInteresse = new Interesse(segmento, colaboradorId);

                interesseRepository.save(novoInteresse);
                interesses.add(novoInteresse);
            }
        }
    }

    public List<Integer> consultarInteresses(Integer colaboradorId) {
        List<Integer> interesses = interesseRepository.findIdByUsuarioId(colaboradorId);

        return interesses;
    }
}
