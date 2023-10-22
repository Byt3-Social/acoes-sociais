package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Doacao;
import com.byt3social.acoessociais.enums.MetodoDoacao;
import com.byt3social.acoessociais.enums.StatusDoacao;
import com.byt3social.acoessociais.models.AcaoVoluntariado;
import com.byt3social.acoessociais.models.Doador;
import com.byt3social.acoessociais.dto.DoacaoDTO;
import com.byt3social.acoessociais.dto.LinkDTO;
import com.byt3social.acoessociais.dto.PagseguroCancelamentoDTO;
import com.byt3social.acoessociais.dto.QrCodeDTO;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class DoacaoRepositoryTest {

    @Autowired
    private DoacaoRepository doacaoRepository;

    @Autowired
    private AcaoVoluntariadoRepository acaoVoluntariadoRepository;

    @Autowired
    private DoadorRepository doadorRepository;

    @Test
    public void testSalvarDoacao() {

        DoacaoDTO doacaoDTO = new DoacaoDTO(
            "John Doe",
            "john@example.com",
            "123",
            "555-123456",
            "123456789",
            MetodoDoacao.PIX,
            null,
            "token",
            "123",
            1,
            100.0,
            1
        );

        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        Doador doador = new Doador();
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);

        Doacao savedDoacao = doacaoRepository.save(doacao);
        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(savedDoacao.getId());

        assertTrue(retrievedDoacao.isPresent());
        Doacao retrieved = retrievedDoacao.get();
        assertEquals(MetodoDoacao.PIX, retrieved.getMetodoDoacao());
    }

    @Test
    public void testBuscarDoacaoPeloId() {

        DoacaoDTO doacaoDTO = new DoacaoDTO(
            "John Doe",
            "john@example.com",
            "123",
            "555-123456",
            "123456789",
            MetodoDoacao.PIX,
            null,
            "token",
            "123",
            1,
            100.0,
            1
        );

        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        Doador doador = new Doador();
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);

        Doacao savedDoacao = doacaoRepository.save(doacao);
        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(savedDoacao.getId());

        assertTrue(retrievedDoacao.isPresent());
    }

    @Test
    public void testAtualizarStatus() {
        DoacaoDTO doacaoDTO = new DoacaoDTO("John Doe", "john@example.com", "123", "555-123456", "123456789",
                MetodoDoacao.PIX, null, "token", "123", 1, 100.0, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        Doador doador = new Doador();
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);
        Doacao savedDoacao = doacaoRepository.save(doacao);

        savedDoacao.atualizarStatus(StatusDoacao.AUTHORIZED);
        doacaoRepository.save(savedDoacao);

        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(savedDoacao.getId());
        assertTrue(retrievedDoacao.isPresent());
        assertEquals(StatusDoacao.AUTHORIZED, retrievedDoacao.get().getStatusDoacao());
    }

    @Test
    public void testAtualizarLinkPagamentoPix() {
        DoacaoDTO doacaoDTO = new DoacaoDTO("John Doe", "john@example.com", "123", "555-123456", "123456789",
                MetodoDoacao.PIX, null, "token", "123", 1, 100.0, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        Doador doador = new Doador();
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);
        Doacao savedDoacao = doacaoRepository.save(doacao);

        QrCodeDTO qrCodeDTO = new QrCodeDTO("text", List.of(new LinkDTO("image/png", "image_link")));
        savedDoacao.atualizarLinkPagamentoPix(qrCodeDTO);
        doacaoRepository.save(savedDoacao);

        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(savedDoacao.getId());
        assertTrue(retrievedDoacao.isPresent());
        assertEquals(qrCodeDTO.text(), retrievedDoacao.get().getQrcodeText());
        assertEquals(qrCodeDTO.links().get(0).href(), retrievedDoacao.get().getLink());
    }

    @Test
    public void testAtualizarLinkPagamentoBoleto() {
        DoacaoDTO doacaoDTO = new DoacaoDTO("John Doe", "john@example.com", "123", "555-123456", "123456789",
                MetodoDoacao.PIX, null, "token", "123", 1, 100.0, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        Doador doador = new Doador();
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);
        Doacao savedDoacao = doacaoRepository.save(doacao);

        List<LinkDTO> links = List.of(new LinkDTO("application/pdf", "pdf_link"));
        savedDoacao.atualizarLinkPagamentoBoleto(links);
        doacaoRepository.save(savedDoacao);

        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(savedDoacao.getId());
        assertTrue(retrievedDoacao.isPresent());
        assertEquals(links.get(0).href(), retrievedDoacao.get().getLink());
    }

    @Test
    public void testAtualizarIdentificador() {
        DoacaoDTO doacaoDTO = new DoacaoDTO("John Doe", "john@example.com", "123", "555-123456", "123456789",
                MetodoDoacao.PIX, null, "token", "123", 1, 100.0, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        Doador doador = new Doador();
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);
        Doacao savedDoacao = doacaoRepository.save(doacao);

        String newIdentifier = "new_identifier";
        savedDoacao.atualizarIdentificador(newIdentifier);
        doacaoRepository.save(savedDoacao);

        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(savedDoacao.getId());
        assertTrue(retrievedDoacao.isPresent());
        assertEquals(newIdentifier, retrievedDoacao.get().getCodigo());
    }

    @Test
    public void testCancelarDoacao() {
        DoacaoDTO doacaoDTO = new DoacaoDTO("John Doe", "john@example.com", "123", "555-123456", "123456789",
                MetodoDoacao.PIX, null, "token", "123", 1, 100.0, 1);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        Doador doador = new Doador();
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);
        Doacao savedDoacao = doacaoRepository.save(doacao);

        PagseguroCancelamentoDTO cancelamentoDTO = new PagseguroCancelamentoDTO(StatusDoacao.CANCELED);
        savedDoacao.cancelar(cancelamentoDTO);
        doacaoRepository.save(savedDoacao);

        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(savedDoacao.getId());
        assertTrue(retrievedDoacao.isPresent());
        assertEquals(StatusDoacao.CANCELED, retrievedDoacao.get().getStatusDoacao());
    }

    @Test
    public void testVincularTransacao() {

        Doador doador = new Doador();
        doador = doadorRepository.save(doador);
        AcaoVoluntariado acaoVoluntariado = new AcaoVoluntariado();
        acaoVoluntariado = acaoVoluntariadoRepository.save(acaoVoluntariado);
        DoacaoDTO doacaoDTO = new DoacaoDTO("John Doe", "john@example.com", "123", "555-123456", "123456789",
                MetodoDoacao.PIX, null, "token", "123", acaoVoluntariado.getId(), 100.0, 1);
        Doacao doacao = new Doacao(doacaoDTO, acaoVoluntariado, doador);

        doacao = doacaoRepository.save(doacao);
        String transacaoID = "12345";
        doacao.vincularTransacaoPagseguro(transacaoID);
        doacaoRepository.save(doacao);

        Optional<Doacao> retrievedDoacao = doacaoRepository.findById(doacao.getId());
        assertTrue(retrievedDoacao.isPresent());
        assertEquals(transacaoID, retrievedDoacao.get().getCodigo());
    }
}
