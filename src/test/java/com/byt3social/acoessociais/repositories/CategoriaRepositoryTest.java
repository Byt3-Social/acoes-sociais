package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Categoria;
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
public class CategoriaRepositoryTest {

    @Autowired
    private CategoriaRepository categoriaRepository;

    @Test
    public void testSalvarCategoria() {

        Categoria categoria = new Categoria("Sample Category");

        Categoria savedCategoria = categoriaRepository.save(categoria);
        Optional<Categoria> retrievedCategoria = categoriaRepository.findById(savedCategoria.getId());

        assertTrue(retrievedCategoria.isPresent());
        Categoria retrieved = retrievedCategoria.get();
        assertEquals("Sample Category", retrieved.getNome());
    }

    @Test
    public void testBuscarCategoriaPeloId() {

        Categoria categoria = new Categoria("Sample Category");

        Categoria savedCategoria = categoriaRepository.save(categoria);
        Optional<Categoria> retrievedCategoria = categoriaRepository.findById(savedCategoria.getId());

        assertTrue(retrievedCategoria.isPresent());
    }

    @Test
    public void testListarTodasCategorias() {

        List<Categoria> allCategorias = categoriaRepository.findAll();

        Categoria categoria1 = new Categoria("Category 1");
        Categoria categoria2 = new Categoria("Category 2");

        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);
        allCategorias.add(categoria1);
        allCategorias.add(categoria2);

        List<Categoria> allCategoriasBD = categoriaRepository.findAll();

        assertNotNull(allCategorias);
        assertEquals(allCategorias.size(), allCategoriasBD.size());
    }
}
