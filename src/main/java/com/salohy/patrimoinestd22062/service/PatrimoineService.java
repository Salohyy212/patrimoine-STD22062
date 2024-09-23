package com.salohy.patrimoinestd22062.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.salohy.patrimoinestd22062.model.Patrimoine;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.HashMap;
import java.util.Map;

@Service
public class PatrimoineService {

    private static final String FILE_PATH = "patrimoines.json";
    private ObjectMapper objectMapper;

    private Map<String, Patrimoine> patrimoineMap = new HashMap<>();

    public PatrimoineService() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
    }

    public void savePatrimoine(String id, Patrimoine patrimoine) throws IOException {
        patrimoineMap.put(id, patrimoine);
        String patrimoineJson = objectMapper.writeValueAsString(patrimoineMap);
        Path path = Paths.get(FILE_PATH);
        byte[] bytes = patrimoineJson.getBytes();
        Files.write(path, bytes, StandardOpenOption.CREATE);
    }

    public Patrimoine getPatrimoine(String id) throws IOException {
        if (!Files.exists(Paths.get(FILE_PATH))) {
            return null;
        }
        byte[] jsonData = Files.readAllBytes(Paths.get(FILE_PATH));
        Map<String, Patrimoine> loadedPatrimoines = objectMapper.readValue(jsonData, new TypeReference<Map<String, Patrimoine>>() {});
        return loadedPatrimoines.get(id);
    }

}
