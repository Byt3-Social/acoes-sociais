package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.dto.AcaoVoluntariadoDTO;
import com.byt3social.acoessociais.enums.*;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Contrato;
import com.byt3social.acoessociais.models.Segmento;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.sql.Time;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

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

    @Test
    public void testAtualizarAcaoVoluntariadoComDTO() {

        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createSampleAcaoVoluntariadoDTO();
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        AcaoVoluntariado savedAcaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);

        AcaoVoluntariadoDTO acaoVoluntariadoDTO2 = new AcaoVoluntariadoDTO(
            "Updated Name",
            Nivel.N2,
            Fase.CRIADA,
            Formato.HIBRIDO,
            Tipo.MENTORIA,
            LocalDate.now(),
            LocalDate.now(),
            new Time(System.currentTimeMillis()).toString(),
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
            null,
            1,
            1,
                1);



        savedAcaoVoluntariado.atualizar(acaoVoluntariadoDTO2);

        acaoVoluntariadoRepository.save(savedAcaoVoluntariado);
        AcaoVoluntariado updatedAcaoVoluntariado = acaoVoluntariadoRepository
                .findById(savedAcaoVoluntariado.getId())
                .orElse(null);

        assertNotNull(updatedAcaoVoluntariado);
        assertEquals("Updated Name", updatedAcaoVoluntariado.getNomeAcao());
        assertEquals(Nivel.N2, updatedAcaoVoluntariado.getNivel());
    }

    @Test
    public void testAtualizarSegmento() {

        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createSampleAcaoVoluntariadoDTO();
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        AcaoVoluntariado savedAcaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);
        Segmento novoSegmento = new Segmento();
        novoSegmento.setNome("Novo Segmento");
        savedAcaoVoluntariado.atualizarSegmento(novoSegmento);
        acaoVoluntariadoRepository.save(savedAcaoVoluntariado);

        AcaoVoluntariado updatedAcaoVoluntariado = acaoVoluntariadoRepository
                .findById(savedAcaoVoluntariado.getId())
                .orElse(null);

        assertNotNull(updatedAcaoVoluntariado);
        assertEquals("Novo Segmento", updatedAcaoVoluntariado.getSegmento().getNome());
    }

    @Test
    public void testSalvarImagem() {

        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createSampleAcaoVoluntariadoDTO();
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        AcaoVoluntariado savedAcaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);
        String caminhoImagem = "caminho/para/imagem.jpg";
        savedAcaoVoluntariado.salvarImagem(caminhoImagem);
        acaoVoluntariadoRepository.save(savedAcaoVoluntariado);

        AcaoVoluntariado updatedAcaoVoluntariado = acaoVoluntariadoRepository
                .findById(savedAcaoVoluntariado.getId())
                .orElse(null);

        assertNotNull(updatedAcaoVoluntariado);
        assertEquals(caminhoImagem, updatedAcaoVoluntariado.getImagem());
    }

    @Test
    public void testExcluirImagem() {

        AcaoVoluntariadoDTO acaoVoluntariadoDTO = createSampleAcaoVoluntariadoDTO();
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(acaoVoluntariadoDTO);
        AcaoVoluntariado savedAcaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);
        String caminhoImagem = "caminho/para/imagem.jpg";
        savedAcaoVoluntariado.salvarImagem(caminhoImagem);
        acaoVoluntariadoRepository.save(savedAcaoVoluntariado);

        savedAcaoVoluntariado.excluirImagem();
        acaoVoluntariadoRepository.save(savedAcaoVoluntariado);
        AcaoVoluntariado updatedAcaoVoluntariado = acaoVoluntariadoRepository
                .findById(savedAcaoVoluntariado.getId())
                .orElse(null);

        assertNotNull(updatedAcaoVoluntariado);
        assertNull(updatedAcaoVoluntariado.getImagem());
    }

    @Test
    public void testIncluirContrato() {

        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(createSampleAcaoVoluntariadoDTO());
        Contrato novoContrato = new Contrato(null, "12345", null, null, null, null, null, null, null);

        acaoVoluntariado.incluirContrato(novoContrato);

        assertEquals(novoContrato, acaoVoluntariado.getContrato());
    }

    @Test
    public void testExcluirContrato() {

        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado(createSampleAcaoVoluntariadoDTO());
        Contrato contrato = new Contrato(null, "12345", null, null, null, null, null, acaoVoluntariado, null);
        acaoVoluntariado.setContrato(contrato);

        acaoVoluntariado.excluirContrato();

        assertNull(acaoVoluntariado.getContrato());
    }

    private AcaoVoluntariadoDTO createSampleAcaoVoluntariadoDTO() {
        return new AcaoVoluntariadoDTO(
            "Sample Acao",
            Nivel.N1,
            Fase.CRIADA,
            Formato.HIBRIDO,
            Tipo.MENTORIA,
            LocalDate.now(),
            LocalDate.now(),
            new Time(System.currentTimeMillis()).toString(),
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
            null,
            1,
            1,
                1
        );
    }
}
