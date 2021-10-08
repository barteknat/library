package com.crud.library.domain;

import com.crud.library.status.ExemplarStatus;
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
    @Enumerated(EnumType.STRING)
    private ExemplarStatus status;
    @ManyToOne
    @JoinColumn(name = "BOOK_ID")
    private Book book;
}
