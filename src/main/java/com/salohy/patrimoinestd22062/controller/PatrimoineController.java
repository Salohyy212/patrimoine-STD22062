package com.salohy.patrimoinestd22062.controller;


import com.salohy.patrimoinestd22062.model.Patrimoine;
import com.salohy.patrimoinestd22062.service.PatrimoineService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private final PatrimoineService patrimoineService;

    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable Long id) {
        Patrimoine patrimoine = patrimoineService.getPatrimoine(id);
        if (patrimoine == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(patrimoine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patrimoine> createOrUpdatePatrimoine(@PathVariable Long id, @RequestBody Patrimoine patrimoine) {
        Patrimoine savedPatrimoine = patrimoineService.saveOrUpdatePatrimoine(id, patrimoine);
        return ResponseEntity.ok(savedPatrimoine);
    }
}

