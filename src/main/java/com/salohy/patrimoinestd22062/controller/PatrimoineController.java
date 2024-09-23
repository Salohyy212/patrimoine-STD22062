package com.salohy.patrimoinestd22062.controller;

import com.salohy.patrimoinestd22062.model.Patrimoine;
import com.salohy.patrimoinestd22062.service.PatrimoineService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;


@RestController
@RequestMapping("/patrimoines")
public class PatrimoineController {

    private final PatrimoineService patrimoineService;

    public PatrimoineController(PatrimoineService patrimoineService) {
        this.patrimoineService = patrimoineService;
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> createOrUpdatePatrimoine(@PathVariable String id, @RequestBody Patrimoine patrimoine) {
        if (patrimoine == null || patrimoine.getPossesseur() == null || patrimoine.getPossesseur().isEmpty()) {
            return ResponseEntity.badRequest().body("Invalid patrimoine data");
        }

        try {
            patrimoineService.savePatrimoine(id, patrimoine);
            return ResponseEntity.ok("Patrimoine saved or updated");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred");
        }
    }



    @GetMapping("/{id}")
    public ResponseEntity<Patrimoine> getPatrimoine(@PathVariable String id) {
        try {
            Patrimoine patrimoine = patrimoineService.getPatrimoine(id);
            if (patrimoine != null) {
                return ResponseEntity.ok(patrimoine);
            } else {
                return ResponseEntity.notFound().build();
            }
        } catch (IOException e) {
            return ResponseEntity.status(500).build();
        }
    }
}
