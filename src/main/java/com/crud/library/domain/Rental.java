package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "RENTALS")
public class Rental {

    @Id
    @GeneratedValue
    private long id;
    private LocalDate rentDate;
    private LocalDate returnDate;
    @ManyToOne
    @JoinColumn(name = "EXEMPLAR_ID")
    private Exemplar exemplar;
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;
}
