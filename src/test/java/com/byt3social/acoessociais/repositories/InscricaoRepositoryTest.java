package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.enums.*;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Inscricao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class InscricaoRepositoryTest {

    @Autowired
    private InscricaoRepository inscricaoRepository;

    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;

    @Test
    public void testSalvarInscricao() {

        AcaoVoluntariadoDTO acaoVoluntariadoDTO = new AcaoVoluntariadoDTO("nome_da_acao", Nivel.N1, Fase.EM_ANDAMENTO, Formato.HIBRIDO, Tipo.MENTORIA, LocalDate.now(), LocalDate.now(), null, "campo grande", "informações_extras", 1, 25.00, TipoMeta.DOACOES, true, true, true, 5, "sobre a organização", "sobre a ação", null, 1, 1, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        acaoVoluntariadoRepository.save(acaoVoluntariado);

        Inscricao inscricao = new Inscricao(1, acaoVoluntariado);

        Inscricao savedInscricao = inscricaoRepository.save(inscricao);
        Optional<Inscricao> retrievedInscricao = inscricaoRepository.findById(savedInscricao.getId());

        assertTrue(retrievedInscricao.isPresent());
        Inscricao retrieved = retrievedInscricao.get();
        assertEquals(StatusInscricao.CONFIRMADA, retrieved.getStatus());
    }

    @Test
    public void testCancelarInscricao() {
        AcaoVoluntariadoDTO acaoVoluntariadoDTO = new AcaoVoluntariadoDTO("nome_da_acao", Nivel.N1, Fase.EM_ANDAMENTO, Formato.HIBRIDO, Tipo.MENTORIA, LocalDate.now(), LocalDate.now(), null, "campo grande", "informações_extras", 1, 25.00, TipoMeta.DOACOES, true, true, true, 5, "sobre a organização", "sobre a ação", null, 1, 1, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        acaoVoluntariadoRepository.save(acaoVoluntariado);

        Inscricao inscricao = new Inscricao(1, acaoVoluntariado);

        Inscricao savedInscricao = inscricaoRepository.save(inscricao);
        savedInscricao.cancelar();
        inscricaoRepository.save(savedInscricao);

        Optional<Inscricao> retrievedInscricao = inscricaoRepository.findById(savedInscricao.getId());
        assertTrue(retrievedInscricao.isPresent());
        assertEquals(StatusInscricao.CANCELADA, retrievedInscricao.get().getStatus());
    }

}
