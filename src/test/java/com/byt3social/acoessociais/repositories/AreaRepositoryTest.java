package com.byt3social.acoessociais.repositories;

import com.byt3social.acoessociais.models.Area;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@ActiveProfiles("test")
public class AreaRepositoryTest {
    
     @Autowired
    private AreaRepository areaRepository;

    @Test
    public void testCriarArea() {

        Area area = new Area("Sample Area");
        Area savedArea = areaRepository.save(area);
        Area retrievedArea = areaRepository.findById(savedArea.getId()).orElse(null);

        assertNotNull(retrievedArea);
        assertEquals("Sample Area", retrievedArea.getNome());
    }

    @Test
    public void testAtualizarArea() {
        Area area = new Area("Sample Area");
        Area savedArea = areaRepository.save(area);

        savedArea.setNome("Updated Area");
        areaRepository.save(savedArea);
        Area updatedArea = areaRepository.findById(savedArea.getId()).orElse(null);

        assertNotNull(updatedArea);
        assertEquals("Updated Area", updatedArea.getNome());
    }

    @Test
    public void testExcluirArea() {

        Area area = new Area("Sample Area");
        Area savedArea = areaRepository.save(area);
        areaRepository.delete(savedArea);
        Area deletedArea = areaRepository.findById(savedArea.getId()).orElse(null);

        assertNull(deletedArea);
    }
}
