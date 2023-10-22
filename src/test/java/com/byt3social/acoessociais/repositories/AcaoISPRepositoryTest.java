package com.byt3social.acoessociais.repositories;
import com.byt3social.acoessociais.models.AcaoISP;
import com.byt3social.acoessociais.models.Contrato;
import com.byt3social.acoessociais.models.LocalImpactado;
import com.byt3social.acoessociais.dto.AcaoISPDTO;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
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
public class AcaoISPRepositoryTest {

    @Autowired
    private AcaoISPRepository acaoISPRepository;

    @Test
    public void testCriarAcaoISP() {

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

        AcaoISP savedAcaoISP = acaoISPRepository.save(acaoISP);

        AcaoISP retrievedAcaoISP = acaoISPRepository.findById(savedAcaoISP.getId()).orElse(null);

        assertNotNull(retrievedAcaoISP);
        assertEquals(acaoISPDTO.nomeAcao(), retrievedAcaoISP.getNomeAcao());
        assertEquals(acaoISPDTO.descricao(), retrievedAcaoISP.getDescricao());
        
    }

    @Test
    public void testAdicionarLocaisImpactados() {

        AcaoISP acaoISP = new AcaoISP();
        acaoISP.setLocaisImpactados(new ArrayList<>());
        List<LocalImpactado> locaisImpactados = new ArrayList<>();

        locaisImpactados.add(new LocalImpactado("Location1", acaoISP));
        locaisImpactados.add(new LocalImpactado("Location2", acaoISP));
        acaoISP.adicionarLocaisImpactados(locaisImpactados);

        assertNotNull(acaoISP.getLocaisImpactados());
        assertEquals(2, acaoISP.getLocaisImpactados().size());
    }

    @Test
    public void testAtualizar() {

        AcaoISP acaoISP = new AcaoISP();
        acaoISP.setNomeAcao("InitialName");
        acaoISP.setDescricao("InitialDescription");
        acaoISP.setAbrangencia(Abrangencia.NACIONAL);
        acaoISP.setTipoInvestimento(TipoInvestimento.PRIVADO);
        acaoISP.setQtdePessoasImpactadas(1000);
        acaoISP.setAporteInicial(10000.0);
        acaoISP.setStatus(StatusISP.EM_ANDAMENTO);

        AcaoISPDTO acaoISPDTO = new AcaoISPDTO(
            "UpdatedName",
            "UpdatedDescription",
            Abrangencia.ESTADUAL,
            TipoInvestimento.INCENTIVO,
            2000,
            20000.0,
            StatusISP.FINALIZADO,
            List.of("Location3", "Location4"),
            null,
            1,
            2,
            3,
            4
        );

        acaoISP.atualizar(acaoISPDTO, null, null, null);

        assertEquals("UpdatedName", acaoISP.getNomeAcao());
        assertEquals("UpdatedDescription", acaoISP.getDescricao());
        assertEquals(Abrangencia.ESTADUAL, acaoISP.getAbrangencia());
        assertEquals(TipoInvestimento.INCENTIVO, acaoISP.getTipoInvestimento());
        assertEquals(2000, acaoISP.getQtdePessoasImpactadas());
        assertEquals(20000.0, acaoISP.getAporteInicial());
        assertEquals(StatusISP.FINALIZADO, acaoISP.getStatus());
    }

    @Test
    public void testIncluirContrato() {

        AcaoISP acaoISP = new AcaoISP();
        Contrato contrato = new Contrato();

        acaoISP.incluirContrato(contrato);

        assertEquals(contrato, acaoISP.getContrato());
    }
}
