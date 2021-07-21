package com.crud.library.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "LENDS")
public class Lend {

    @Id
    @GeneratedValue
    private long id;
    private LocalDate lendDate;
    private LocalDate returnDate;
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private BookItem bookItem;
    @ManyToOne
    private User user;
}
