package com.crud.library.dto;

import com.crud.library.status.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExemplarDto {

    private long id;
    private RentalStatus status;
}
