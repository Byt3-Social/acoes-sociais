package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Segmento;
import com.byt3social.acoessociais.dto.SegmentoDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class SegmentoRepositoryTest {

    @Autowired
    private SegmentoRepository segmentoRepository;

    @Test
    public void testSalvarSegmento() {
        SegmentoDTO segmentoDTO = new SegmentoDTO("Nome do Segmento");
        Segmento segmento = new Segmento(segmentoDTO);

        Segmento savedSegmento = segmentoRepository.save(segmento);

        Optional<Segmento> retrievedSegmento = segmentoRepository.findById(savedSegmento.getId());
        assertNotNull(retrievedSegmento.orElse(null));
        assertEquals("Nome do Segmento", retrievedSegmento.get().getNome());
    }
}
