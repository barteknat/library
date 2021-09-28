package com.crud.library.domain;

import com.crud.library.status.RentalStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "EXEMPLARS")
public class Exemplar {

    @Id
    @GeneratedValue
    private long id;
    private RentalStatus status;
    @ManyToOne
    private Book book;
}
