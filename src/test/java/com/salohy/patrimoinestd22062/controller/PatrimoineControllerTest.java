package com.salohy.patrimoinestd22062.controller;

import com.salohy.patrimoinestd22062.model.Patrimoine;
import com.salohy.patrimoinestd22062.service.PatrimoineService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import java.time.LocalDateTime;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatrimoineController.class)
public class PatrimoineControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatrimoineService patrimoineService;

    @Test
    public void testCreateOrUpdatePatrimoine() throws Exception {
        Patrimoine patrimoine = new Patrimoine("Zety", LocalDateTime.now());

        doNothing().when(patrimoineService).savePatrimoine(any(String.class), any(Patrimoine.class));

        mockMvc.perform(put("/patrimoines/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"possesseur\":\"Zety\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Patrimoine saved or updated"));
    }

    @Test
    public void testGetPatrimoine() throws Exception {
        Patrimoine patrimoine = new Patrimoine("Zety", LocalDateTime.now());

        when(patrimoineService.getPatrimoine("123")).thenReturn(patrimoine);

        mockMvc.perform(get("/patrimoines/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.possesseur").value("Zety"));
    }
    @Test
    public void testCreateOrUpdatePatrimoineBadRequest() throws Exception {
        mockMvc.perform(put("/patrimoines/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"possesseur\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("Invalid patrimoine data"));
    }

    @Test
    public void testGetPatrimoineNotFound() throws Exception {
        when(patrimoineService.getPatrimoine("123")).thenReturn(null);

        mockMvc.perform(get("/patrimoines/123"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testCreateOrUpdatePatrimoineInternalServerError() throws Exception {
        doThrow(new RuntimeException("Service Error")).when(patrimoineService).savePatrimoine(any(String.class), any(Patrimoine.class));

        mockMvc.perform(put("/patrimoines/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"possesseur\":\"Zety\"}"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string("An error occurred"));
    }


}
