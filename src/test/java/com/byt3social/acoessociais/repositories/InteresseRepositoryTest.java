package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.dto.SegmentoDTO;
import com.byt3social.acoessociais.models.Interesse;
import com.byt3social.acoessociais.models.Segmento;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class InteresseRepositoryTest {

    @Autowired
    private InteresseRepository interesseRepository;

    @Autowired
    private SegmentoRepository segmentoRepository; 

    @Test
    public void testSalvarInteresse() {
        SegmentoDTO segmentoDTO = new SegmentoDTO("nome_segmento");
        Segmento segmento = new Segmento(segmentoDTO);
        segmentoRepository.save(segmento);

        Interesse interesse = new Interesse(segmento, 1);
        Interesse savedInteresse = interesseRepository.save(interesse);

        Optional<Interesse> retrievedInteresse = interesseRepository.findById(savedInteresse.getId());
        assertNotNull(retrievedInteresse.orElse(null));
    }

    @Test
    public void testBuscarInteressesPorUsuarioId() {
        SegmentoDTO segmentoDTO1 = new SegmentoDTO("nome_segmento1");
        SegmentoDTO segmentoDTO2 = new SegmentoDTO("nome_segmento2");
        Segmento segmento1 = new Segmento(segmentoDTO1);
        Segmento segmento2 = new Segmento(segmentoDTO2);

        segmentoRepository.saveAll(List.of(segmento1, segmento2)); 

        Interesse interesse1 = new Interesse(segmento1, 1);
        Interesse interesse2 = new Interesse(segmento2, 1);

        interesseRepository.saveAll(List.of(interesse1, interesse2));

        List<Interesse> interesses = interesseRepository.findByUsuarioId(1);

        assertNotNull(interesses);
        assertEquals(2, interesses.size());
    }
}
