package com.crud.library.controller;

import com.crud.library.domain.Book;
import com.crud.library.domain.Exemplar;
import com.crud.library.domain.User;
import com.crud.library.dto.RentalDto;
import com.crud.library.service.RentalService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;

import static com.crud.library.status.RentalStatus.AVAILABLE;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;

@SpringJUnitWebConfig
@WebMvcTest(RentalController.class)
class RentalControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RentalService rentalService;

    @Test
    void shouldRentExemplarTest() throws Exception {
        //Given
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        User user = new User(1, "Adam", "Smith", "test@test.com", LocalDate.parse("1998-01-01"));
        long userId = user.getId();
        Exemplar exemplar = new Exemplar(1, AVAILABLE, new Book(1, "test_signature", "test_title", "test_author", LocalDate.parse("1990-01-01")));
        long exemplarId = exemplar.getId();
        RentalDto rentalDto = new RentalDto(1, LocalDate.parse("2000-01-01"), LocalDate.parse("2000-01-20"));
        when(rentalService.rentExemplar(userId, exemplarId, rentalDto)).thenReturn(rentalDto);
        String jsonContent = mapper.writeValueAsString(rentalDto);

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/rental/{userId}/{exemplarId}", userId, exemplarId)
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8"))
                .andDo(print())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.rentDate", Matchers.is("2000-01-01")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.returnDate", Matchers.is("2000-01-20")));
    }

    @Test
    void shouldGiveBackExemplar() throws Exception {
        //Given
        long rentId = 1;
        String date = "2000-01-01";

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/rental/{id}", rentId)
                        .param("date", date))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void shouldPayPenaltyForExemplar() throws Exception {
        //Given
        long rentId = 1;
        String charge = "payForExemplar";

        //When & Then
        mockMvc
                .perform(MockMvcRequestBuilders
                        .put("/v1/rental/payPenalty/{id}", rentId)
                        .param("charge", charge))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
