package com.backend.todo.unit.controller;

import com.backend.todo.controller.StatController;
import com.backend.todo.service.StatService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Alexey Voronin.
 * @since 10.12.2022.
 */
@WebMvcTest(StatController.class)
@RequiredArgsConstructor
public class StatControllerTest {

    private final MockMvc mockMvc;

    @MockBean
    private StatService statService;

    @Test
    public void findAll() throws Exception {
        mockMvc.perform(get("/api/v1/stats"))
                .andExpect(status().is2xxSuccessful());

        verify(statService, times(1)).findAll();
    }
}
