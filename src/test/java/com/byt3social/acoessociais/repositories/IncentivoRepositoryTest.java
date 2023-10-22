package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Incentivo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class IncentivoRepositoryTest {

    @Autowired
    private IncentivoRepository incentivoRepository;

    @Test
    public void testSalvarIncentivo() {

        Incentivo incentivo = new Incentivo("Incentivo Teste");

        Incentivo savedIncentivo = incentivoRepository.save(incentivo);
        Optional<Incentivo> retrievedIncentivo = incentivoRepository.findById(savedIncentivo.getId());

        assertTrue(retrievedIncentivo.isPresent());
        Incentivo retrieved = retrievedIncentivo.get();
        assertEquals("Incentivo Teste", retrieved.getNome());
    }

    @Test
    public void testAtualizarIncentivo() {
        Incentivo incentivo = new Incentivo("Incentivo Teste");
        Incentivo savedIncentivo = incentivoRepository.save(incentivo);

        savedIncentivo.setNome("Novo Incentivo");
        incentivoRepository.save(savedIncentivo);

        Optional<Incentivo> retrievedIncentivo = incentivoRepository.findById(savedIncentivo.getId());
        assertTrue(retrievedIncentivo.isPresent());
        assertEquals("Novo Incentivo", retrievedIncentivo.get().getNome());
    }
}
