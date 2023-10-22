package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Time;
import java.util.Date;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.byt3social.acoessociais.enums.Nivel;
import com.byt3social.acoessociais.enums.Formato;
import com.byt3social.acoessociais.enums.Fase;
import com.byt3social.acoessociais.enums.Tipo;
import com.byt3social.acoessociais.enums.TipoMeta;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AcaoVoluntariadoRepositoryTest {

    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;

    @Test
    public void testCriarAcaoVoluntariado() {
        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createSampleAcaoVoluntariadoDTO();

        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);

        AcaoVoluntariado savedAcaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);

        AcaoVoluntariado retrievedAcaoVoluntariado = acaoVoluntariadoRepository
                .findById(savedAcaoVoluntariado.getId())
                .orElse(null);

        assertNotNull(retrievedAcaoVoluntariado);
        assertEquals(acaoVoluntariadoDTO.nomeAcao(), retrievedAcaoVoluntariado.getNomeAcao());
        assertEquals(acaoVoluntariadoDTO.nivel(), retrievedAcaoVoluntariado.getNivel());
    }

    @Test
    public void testAtualizarAcaoVoluntariado() {

        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createSampleAcaoVoluntariadoDTO();
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        AcaoVoluntariado savedAcaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);

        savedAcaoVoluntariado.setNomeAcao("Updated Name");
        savedAcaoVoluntariado.setNivel(Nivel.N2);
        savedAcaoVoluntariado.setFase(Fase.EM_ANDAMENTO);

        acaoVoluntariadoRepository.save(savedAcaoVoluntariado);
        AcaoVoluntariado updatedAcaoVoluntariado = acaoVoluntariadoRepository
                .findById(savedAcaoVoluntariado.getId())
                .orElse(null);

        assertNotNull(updatedAcaoVoluntariado);
        assertEquals("Updated Name", updatedAcaoVoluntariado.getNomeAcao());
        assertEquals(Nivel.N2, updatedAcaoVoluntariado.getNivel());
    }

    private AcaoVoluntariadoDTO createSampleAcaoVoluntariadoDTO() {
        return new AcaoVoluntariadoDTO(
            "Sample Acao",
            Nivel.N1,
            Fase.CRIADA,
            Formato.HIBRIDO,
            Tipo.MENTORIA,
            new Date(),
            new Date(),
            new Time(System.currentTimeMillis()),
            "Sample Location",
            "Sample Informacoes Adicionais",
            100,
            1000.0,
            TipoMeta.DOACOES,
            true,
            true,
            false,
            2,
            "Sample Sobre Organizacao",
            "Sample Sobre Acao",
            1,
            1,
            1
        );
    }
}
