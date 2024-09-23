package com.salohy.patrimoinestd22062.service;

import com.salohy.patrimoinestd22062.model.Patrimoine;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;


public class PatrimoineServiceTest {

    private PatrimoineService patrimoineService;

    @BeforeEach
    public void setUp() {
        patrimoineService = new PatrimoineService();
    }

    @AfterEach
    public void tearDown() throws IOException {
        Files.deleteIfExists(Paths.get("patrimoines.json"));
    }

    @Test
    public void testSavePatrimoine() throws Exception {
        Patrimoine patrimoine = new Patrimoine("Zety", LocalDateTime.now());
        patrimoineService.savePatrimoine("123", patrimoine);

        Patrimoine retrieved = patrimoineService.getPatrimoine("123");
        assertNotNull(retrieved);
        assertEquals("Zety", retrieved.getPossesseur());
    }

    @Test
    public void testGetPatrimoineNotFound() throws Exception {
        Patrimoine retrieved = patrimoineService.getPatrimoine("non-existent-id");
        assertNull(retrieved);
    }

}
