package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Doador;
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
public class DoadorRepositoryTest {

    @Autowired
    private DoadorRepository doadorRepository;

    @Test
    public void testSalvarDoador() {
        Doador doador = new Doador();
        doador.setNome("John Doe");
        doador.setCpf("12345678900");
        doador.setEmail("john@example.com");
        doador.setTelefone("555-123456");

        Doador savedDoador = doadorRepository.save(doador);
        Optional<Doador> retrievedDoador = doadorRepository.findById(savedDoador.getId());

        assertTrue(retrievedDoador.isPresent());
        Doador retrieved = retrievedDoador.get();
        assertEquals("John Doe", retrieved.getNome());
        assertEquals("12345678900", retrieved.getCpf());
    }

    @Test
    public void testAtualizarDoador() {
        Doador doador = new Doador();
        doador.setNome("John Doe");
        doador.setCpf("12345678900");
        doador.setEmail("john@example.com");
        doador.setTelefone("555-123456");

        Doador savedDoador = doadorRepository.save(doador);
        savedDoador.setEmail("new_email@example.com");
        savedDoador.setTelefone("555-987654");
        doadorRepository.save(savedDoador);

        Optional<Doador> retrievedDoador = doadorRepository.findById(savedDoador.getId());
        assertTrue(retrievedDoador.isPresent());
        assertEquals("John Doe", retrievedDoador.get().getNome());
        assertEquals("new_email@example.com", retrievedDoador.get().getEmail());
        assertEquals("555-987654", retrievedDoador.get().getTelefone());
    }

    @Test
    public void testEncontrarPeloCpf() {

        Doador doador = new Doador();
        doador.setCpf("12345678900");
        doadorRepository.save(doador);
        String cpfToFind = "12345678900";

        Doador foundDoador = doadorRepository.findByCpf(cpfToFind);

        assertNotNull(foundDoador);
        assertEquals(cpfToFind, foundDoador.getCpf());
    }

    @Test
    public void testEncontrarPeloCpfInexistente() {

        String cpfToFind = "98765432100";
        Doador foundDoador = doadorRepository.findByCpf(cpfToFind);

        assertEquals(null, foundDoador);
    }
}
