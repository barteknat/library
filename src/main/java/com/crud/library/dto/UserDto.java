package com.crud.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class UserDto {

    private long id;
    private String firstName;
    private String lastName;
    private String mailAddress;
    private LocalDate signUp;
}
