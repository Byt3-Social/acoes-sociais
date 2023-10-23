package com.byt3social.acoessociais.controllers;

import com.byt3social.acoessociais.dto.InteresseDTO;
import com.byt3social.acoessociais.services.InteresseService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;

@SpringBootTest
@AutoConfigureMockMvc
public class InteresseControllerTest {

    @Autowired
    private InteresseController interesseController;

    @MockBean
    private InteresseService interesseService;

    @Test
    public void testManifestarInteresse() {
        InteresseDTO interesseDTO = new InteresseDTO(List.of(1, 2, 3));

        doNothing().when(interesseService).manisfestarInteresse(interesseDTO,1);

        ResponseEntity response = interesseController.manifestarInteresse(String.valueOf(1), interesseDTO);

        verify(interesseService).manisfestarInteresse(interesseDTO, 1);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
    }
}

