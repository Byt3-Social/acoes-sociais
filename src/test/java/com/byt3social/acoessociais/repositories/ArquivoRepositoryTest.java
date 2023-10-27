package com.byt3social.acoessociais.repositories;
import com.byt3social.acoessociais.models.Arquivo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class ArquivoRepositoryTest {

    @Autowired
    private ArquivoRepository arquivoRepository;

    @Test
    public void testSalvarArquivo() {

        Arquivo arquivo = new Arquivo("sample/caminho", "sample_file.txt", 1024L, null);
        Arquivo savedArquivo = arquivoRepository.save(arquivo);
        Optional<Arquivo> retrievedArquivo = arquivoRepository.findById(savedArquivo.getId());

        assertTrue(retrievedArquivo.isPresent());
        Arquivo retrieved = retrievedArquivo.get();
        assertEquals("sample/caminho", retrieved.getCaminhoS3());
        assertEquals("sample_file.txt", retrieved.getNomeArquivoOriginal());

    }

    @Test
    public void testBuscarArquivoPeloId() {

        Arquivo arquivo = new Arquivo("sample/caminho", "sample_file.txt", 1024L, null);
        Arquivo savedArquivo = arquivoRepository.save(arquivo);
        Optional<Arquivo> retrievedArquivo = arquivoRepository.findById(savedArquivo.getId());

        assertTrue(retrievedArquivo.isPresent());
    }

    @Test
    public void testListarTodosArquivos() {

        Arquivo arquivo1 = new Arquivo("sample/caminho1", "sample_file1.txt", 1024L, null);
        Arquivo arquivo2 = new Arquivo("sample/caminho2", "sample_file2.txt", 2048L, null);
        arquivoRepository.save(arquivo1);
        arquivoRepository.save(arquivo2);
        List<Arquivo> allArquivos = arquivoRepository.findAll();

        assertNotNull(allArquivos);
        assertEquals(2, allArquivos.size());
    }
}
