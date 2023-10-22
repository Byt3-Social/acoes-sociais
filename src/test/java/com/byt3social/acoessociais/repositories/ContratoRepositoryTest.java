package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Contrato;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ContratoRepositoryTest {

    @Autowired
    private ContratoRepository contratoRepository;

    @Test
    public void testSalvarContrato() {

        Contrato contrato = new Contrato("Sample Contract");

        Contrato savedContrato = contratoRepository.save(contrato);
        Optional<Contrato> retrievedContrato = contratoRepository.findById(savedContrato.getId());

        assertTrue(retrievedContrato.isPresent());
        Contrato retrieved = retrievedContrato.get();
        assertEquals("Sample Contract", retrieved.getCaminhoS3());
    }

    @Test
    public void testBuscarContratoPeloId() {

        Contrato contrato = new Contrato("Sample Contract");

        Contrato savedContrato = contratoRepository.save(contrato);
        Optional<Contrato> retrievedContrato = contratoRepository.findById(savedContrato.getId());

        assertTrue(retrievedContrato.isPresent());
    }

    @Test
    public void testListarTodosContratos() {

        Contrato contrato1 = new Contrato("Contract 1");
        Contrato contrato2 = new Contrato("Contract 2");

        contratoRepository.save(contrato1);
        contratoRepository.save(contrato2);

        Iterable<Contrato> allContratos = contratoRepository.findAll();

        assertNotNull(allContratos);
    }
}
