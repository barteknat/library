package com.crud.library.domain;

import com.crud.library.status.LendStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Entity(name = "BOOK_ITEMS")
public class BookItem {

    @Id
    @GeneratedValue
    private long id;
    private LendStatus status;
    @ManyToOne
    private Book book;
}
