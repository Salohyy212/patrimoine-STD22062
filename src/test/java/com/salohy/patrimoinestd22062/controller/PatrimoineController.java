package com.salohy.patrimoinestd22062.controller;

import com.salohy.patrimoinestd22062.model.Patrimoine;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.salohy.patrimoinestd22062.service.PatrimoineService;

@WebMvcTest(PatrimoineController.class)
class PatrimoineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatrimoineService patrimoineService;

    private Patrimoine patrimoine;

    @BeforeEach
    void setUp() {
        patrimoine = new Patrimoine("John Doe", LocalDateTime.now());
    }

    @Test
    void testGetPatrimoine() throws Exception {
        when(patrimoineService.getPatrimoine(1L)).thenReturn(patrimoine);

        mockMvc.perform(get("/patrimoines/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possesseur").value("John Doe"));
    }

    @Test
    void testGetPatrimoineNotFound() throws Exception {
        when(patrimoineService.getPatrimoine(1L)).thenReturn(null);

        mockMvc.perform(get("/patrimoines/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateOrUpdatePatrimoine() throws Exception {
        when(patrimoineService.saveOrUpdatePatrimoine(eq(1L), any(Patrimoine.class)))
                .thenReturn(patrimoine);

        mockMvc.perform(put("/patrimoines/1")
                        .contentType("application/json")
                        .content(new ObjectMapper().writeValueAsString(patrimoine)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possesseur").value("John Doe"));
    }
}

