package com.crud.library.controller;

import com.crud.library.dto.ExemplarDto;
import com.crud.library.service.ExemplarService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static com.crud.library.status.ExemplarStatus.AVAILABLE;
import static com.crud.library.status.ExemplarStatus.DAMAGED;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(ExemplarController.class)
class ExemplarControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ExemplarService exemplarService;

    @Test
    void shouldGetAvailable() throws Exception {
        ExemplarDto exemplarDto = new ExemplarDto(2L, AVAILABLE);
        long id = exemplarDto.getId();
        when(exemplarService.findAvailable(id)).thenReturn(1L);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/v1/exemplar/{id}", id))
                .andExpect(MockMvcResultMatchers.jsonPath("$", Matchers.is(1)));
    }

    @Test
    void shouldCreateExemplar() throws Exception {
        ExemplarDto exemplarDto = new ExemplarDto(1L, AVAILABLE);
        long id = exemplarDto.getId();
        when(exemplarService.createExemplar(id)).thenReturn(exemplarDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/exemplar/{id}", id))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("AVAILABLE")));
    }

    @Test
    void shouldUpdateExemplar() throws Exception {
        ExemplarDto exemplarDto = new ExemplarDto(1L, AVAILABLE);
        ExemplarDto exemplarDtoUpdated = new ExemplarDto(1L, DAMAGED);
        long id = exemplarDto.getId();
        when(exemplarService.updateExemplar(id, DAMAGED)).thenReturn(exemplarDtoUpdated);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/exemplar/{id}", id)
                        .param("status", "DAMAGED"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status", Matchers.is("DAMAGED")));
    }
}
