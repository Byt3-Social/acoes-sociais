package com.byt3social.acoessociais.controllers;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.services.InteresseService;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@AutoConfigureMockMvc
public class InteresseControllerTest {

    @Autowired
    private InteresseController interesseController;

    @MockBean
    private InteresseService interesseService;

    @Test
    public void testManifestarInteresse() {
        InteresseDTO interesseDTO = new InteresseDTO(1, List.of(1, 2, 3));

        doNothing().when(interesseService).manisfestarInteresse(interesseDTO);

        ResponseEntity response = interesseController.manifestarInteresse(interesseDTO);

        verify(interesseService).manisfestarInteresse(interesseDTO);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

