package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.dto.OpcaoContribuicaoDTO;
import com.byt3social.acoessociais.enums.*;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.OpcaoContribuicao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class OpcaoContribuicaoRepositoryTest {

    @Autowired
    private OpcaoContribuicaoRepository opcaoContribuicaoRepository;

    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;

    @Test
    public void testSalvarOpcaoContribuicao() {
        OpcaoContribuicaoDTO opcaoContribuicaoDTO = new OpcaoContribuicaoDTO(1, 100.0, "Descrição da opção");
        AcaoVoluntariadoDTO acaoVoluntariadoDTO = new AcaoVoluntariadoDTO("nome_da_acao", Nivel.N1, Fase.EM_ANDAMENTO, Formato.HIBRIDO, Tipo.MENTORIA, LocalDate.now(), LocalDate.now(), null, "campo grande", "informações_extras", 1, 25.00, TipoMeta.DOACOES, true, true, true, 5, "sobre a organização", "sobre a ação", null, 1, 1, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO); 
        acaoVoluntariadoRepository.save(acaoVoluntariado);

        OpcaoContribuicao opcaoContribuicao = new OpcaoContribuicao(opcaoContribuicaoDTO, acaoVoluntariado);
        OpcaoContribuicao savedOpcaoContribuicao = opcaoContribuicaoRepository.save(opcaoContribuicao);

        Optional<OpcaoContribuicao> retrievedOpcaoContribuicao = opcaoContribuicaoRepository.findById(savedOpcaoContribuicao.getId());
        assertNotNull(retrievedOpcaoContribuicao.orElse(null));
    }
}
