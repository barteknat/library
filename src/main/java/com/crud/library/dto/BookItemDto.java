package com.crud.library.dto;

import com.crud.library.status.LendStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class BookItemDto {

    private long id;
    private LendStatus status;
}
