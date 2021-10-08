package com.crud.library.controller;

import com.crud.library.dto.UserDto;
import com.crud.library.service.UserService;
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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringJUnitWebConfig
@WebMvcTest(UserController.class)
class UserControllerTestSuite {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void shouldCreateUserTest() throws Exception {
        //Given
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        mapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        UserDto userDto = new UserDto(1, "test_firstName", "test_lastName", "test@test.com", LocalDate.parse("2000-01-01"));
        when(userService.createUser(any(UserDto.class))).thenReturn(userDto);
        String jsonContent = mapper.writeValueAsString(userDto);

        mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/v1/user")
                        .content(jsonContent)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .characterEncoding("UTF-8"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName", Matchers.is("test_firstName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName", Matchers.is("test_lastName")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.mailAddress", Matchers.is("test@test.com")))
                .andExpect(MockMvcResultMatchers.jsonPath("$.signUp", Matchers.is("2000-01-01")));
    }
}
