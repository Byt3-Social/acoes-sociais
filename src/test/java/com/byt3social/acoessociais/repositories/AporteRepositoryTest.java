package com.byt3social.acoessociais.repositories;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.models.Aporte;
import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.dto.AporteDTO;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import com.byt3social.acoessociais.enums.Abrangencia;
import com.byt3social.acoessociais.enums.TipoInvestimento;
import com.byt3social.acoessociais.enums.StatusISP;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AporteRepositoryTest {

    @Autowired
    private AporteRepository aporteRepository;

     @Test
    public void testCriarAporte() {

        AcaoISP acaoISP = createSampleAcaoISP();
        AporteDTO aporteDTO = createSampleAporteDTO();
        Aporte aporte = new Aporte(aporteDTO, acaoISP);
        Aporte savedAporte = aporteRepository.save(aporte);
        Aporte retrievedAporte = aporteRepository.findById(savedAporte.getId()).orElse(null);

        assertNotNull(retrievedAporte);
        assertEquals(aporteDTO.valor(), retrievedAporte.getValor());

    }

    private AcaoISP createSampleAcaoISP() {
        AcaoISPDTO acaoISPDTO = new AcaoISPDTO(
                "Acao Test",
                "Description",
                Abrangencia.NACIONAL,
                TipoInvestimento.PRIVADO,
                1000,
                10000.0,
                StatusISP.EM_ANDAMENTO,
                List.of("Location1", "Location2"),
                null, 
                1,     
                2,    
                3,     
                4     
        );

        AcaoISP acaoISP = new AcaoISP(acaoISPDTO, null, null, null);
        return acaoISP; 
    }

    private AporteDTO createSampleAporteDTO() {
        return new AporteDTO(
            null,  
            1000.0,
            new Date()  
        );
    }

}
