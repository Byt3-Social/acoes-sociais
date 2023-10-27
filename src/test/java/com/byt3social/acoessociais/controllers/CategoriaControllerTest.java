package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Categoria;
import com.byt3social.acoessociais.repositories.CategoriaRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class CategoriaControllerTest {

    @Autowired
    private CategoriaController categoriaController;

    @MockBean
    private CategoriaRepository categoriaRepository;


    @Test
    public void testConsultarCategoriasAcaoISP() {
        List<Categoria> categorias = new ArrayList<>();
        Categoria categoria1 = new Categoria("Categoria 1");
        Categoria categoria2 = new Categoria("Categoria 2");
        categorias.add(categoria1);
        categorias.add(categoria2);

        when(categoriaRepository.findAll()).thenReturn(categorias);

        ResponseEntity<List<Categoria>> response = categoriaController.consultarCategoriasAcaoISP();

        verify(categoriaRepository).findAll();
        assertEquals(HttpStatus.OK, response.getStatusCode());
        List<Categoria> returnedCategorias = response.getBody();
        assertNotNull(returnedCategorias);
        assertEquals(2, returnedCategorias.size());
    }

    @Test
    public void testCadastrarCategoriaAcaoISP() {
        String categoriaName = "Nova Categoria";
        Categoria categoria = new Categoria(categoriaName);

        when(categoriaRepository.save(any(Categoria.class))).thenReturn(categoria);

        ResponseEntity response = categoriaController.cadastrarCategoriaAcaoISP(Map.of("nome", categoriaName));

        verify(categoriaRepository).save(any(Categoria.class));
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    public void testExcluirCategoriaAcaoISP() {
        int categoriaId = 1;

        doNothing().when(categoriaRepository).deleteById(categoriaId);

        ResponseEntity response = categoriaController.excluirCategoriaAcaoISP(categoriaId);

        verify(categoriaRepository).deleteById(categoriaId);
        assertEquals(HttpStatus.OK, response.getStatusCode());
    }
}
