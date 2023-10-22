package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.dto.AcaoISPDTO;
import com.byt3social.acoessociais.enums.Abrangencia;
import com.byt3social.acoessociais.enums.StatusISP;
import com.byt3social.acoessociais.enums.TipoInvestimento;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.models.LocalImpactado;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class LocalImpactadoRepositoryTest {

    @Autowired
    private LocalImpactadoRepository localImpactadoRepository;

    @Autowired
    private AcaoISPRepository acaoISPRepository;

    @Test
    public void testSalvarLocalImpactadoParaAcaoISP() {
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
        acaoISPRepository.save(acaoISP);

        LocalImpactado localImpactado = new LocalImpactado("Local Impactado", acaoISP);
        LocalImpactado savedLocalImpactado = localImpactadoRepository.save(localImpactado);

        Optional<LocalImpactado> retrievedLocalImpactado = localImpactadoRepository.findById(savedLocalImpactado.getId());
        assertNotNull(retrievedLocalImpactado.orElse(null));
    }
}
