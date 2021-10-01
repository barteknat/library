package com.crud.library.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookDto {

    private long id;
    private String signature;
    private String title;
    private String author;
    private long year;
}
