package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.models.Incentivo;
import com.byt3social.acoessociais.repositories.IncentivoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class IncentivoControllerTest {

    @Autowired
    private IncentivoController incentivoController;

    @MockBean
    private IncentivoRepository incentivoRepository;
    
    @Test
public void testConsultarIncentivosAcaoISP() {

    Incentivo incentivo1 = new Incentivo("Incentivo Teste 1");
    Incentivo incentivo2 = new Incentivo("Incentivo Teste 2");
    List<Incentivo> incentivos = new ArrayList<>();

    incentivos.add(incentivo1);
    incentivos.add(incentivo2);
    when(incentivoRepository.findAll()).thenReturn(incentivos);
    ResponseEntity<List<Incentivo>> response = incentivoController.consultarIncentivosAcaoISP();

    verify(incentivoRepository).findAll();
    assertEquals(HttpStatus.OK, response.getStatusCode());
    List<Incentivo> returnedIncentivos = response.getBody();
    assertNotNull(returnedIncentivos);
    assertEquals(2, returnedIncentivos.size());
    assertEquals("Incentivo Teste 1", returnedIncentivos.get(0).getNome());
    assertEquals("Incentivo Teste 2", returnedIncentivos.get(1).getNome());
}

@Test
public void testCadastrarIncentivoAcaoISP() {

    Map<String, String> incentivoBody1 = new HashMap<>();
    incentivoBody1.put("nome", "Incentivo Teste 1");
    Map<String, String> incentivoBody2 = new HashMap<>();
    incentivoBody2.put("nome", "Incentivo Teste 2");
    List<Incentivo> incentivos = new ArrayList<>();

    when(incentivoRepository.save(any(Incentivo.class))).thenAnswer(invocation -> {
        Incentivo savedIncentivo = invocation.getArgument(0);
        incentivos.add(savedIncentivo); 
        return savedIncentivo; 
    });
    ResponseEntity response1 = incentivoController.cadastrarIncentivoAcaoISP(incentivoBody1);
    ResponseEntity response2 = incentivoController.cadastrarIncentivoAcaoISP(incentivoBody2);

    verify(incentivoRepository, times(2)).save(any(Incentivo.class));
    assertEquals(2, incentivos.size()); 
    assertEquals("Incentivo Teste 1", incentivos.get(0).getNome());
    assertEquals("Incentivo Teste 2", incentivos.get(1).getNome());
    assertEquals(HttpStatus.CREATED, response1.getStatusCode());
    assertEquals(HttpStatus.CREATED, response2.getStatusCode());
}



    @Test
    public void testExcluirIncentivoAcaoISP() {
        
        int incentivoID = 1;

        doNothing().when(incentivoRepository).deleteById(incentivoID);
        ResponseEntity response = incentivoController.excluirIncentivoAcaoISP(incentivoID);

        verify(incentivoRepository).deleteById(incentivoID);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertFalse(incentivoRepository.existsById(incentivoID));

    }
}
