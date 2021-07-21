package com.crud.library.mapper;

import com.crud.library.domain.Book;
import com.crud.library.dto.BookDto;
import org.mapstruct.Mapper;

//import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

    public abstract Book mapToBook(BookDto bookDto);

    public abstract BookDto mapToBookDto(Book book);

//    public abstract List<BookDto> mapToBookDtoList(List<Book> books);
}
