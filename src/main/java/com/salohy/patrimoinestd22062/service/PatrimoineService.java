package com.salohy.patrimoinestd22062.service;

import com.salohy.patrimoinestd22062.model.Patrimoine;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Service
public class PatrimoineService {
    private final Map<Long, Patrimoine> patrimoines = new HashMap<>();

    public Patrimoine getPatrimoine(Long id) {
        return patrimoines.get(id);
    }

    public Patrimoine saveOrUpdatePatrimoine(Long id, Patrimoine patrimoine) {
        patrimoine.setDerniereModification(LocalDateTime.now());
        patrimoines.put(id, patrimoine);
        return patrimoine;
    }
}

