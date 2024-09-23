package com.salohy.patrimoinestd22062.service;

import com.salohy.patrimoinestd22062.model.Patrimoine;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class PatrimoineService {

    private static final String FILE_PATH = "patrimoines.json";
    private ObjectMapper objectMapper;

    // Simuler un stockage en mémoire temporaire pour les exemples
    private Map<String, Patrimoine> patrimoineMap = new HashMap<>();

    public PatrimoineService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule()); // Pour gérer LocalDateTime
    }

    public void savePatrimoine(String id, Patrimoine patrimoine) throws IOException {
        patrimoineMap.put(id, patrimoine);
        String patrimoineJson = objectMapper.writeValueAsString(patrimoineMap);
        Files.write(Paths.get(FILE_PATH), patrimoineJson.getBytes(), StandardOpenOption.CREATE);
    }

    public Patrimoine getPatrimoine(String id) throws IOException {
        byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
        Map<String, Patrimoine> loadedPatrimoines = objectMapper.readValue(jsonData, patrimoineMap.getClass());
        return loadedPatrimoines.get(id);
    }
}
