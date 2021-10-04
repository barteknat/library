package com.crud.library.dto;

import com.crud.library.status.ExemplarStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class ExemplarDto {

    private long id;
    @Enumerated(EnumType.STRING)
    private ExemplarStatus status;
}
