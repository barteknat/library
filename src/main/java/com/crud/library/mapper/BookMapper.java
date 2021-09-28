package com.crud.library.mapper;

import com.crud.library.domain.Book;
import com.crud.library.dto.BookDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

//import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookMapper {

//    @Mapping(target = "exemplars", ignore = true)
    public abstract Book mapToBook(BookDto bookDto);

//    @Mapping(target = "exemplars", ignore = true)
    public abstract BookDto mapToBookDto(Book book);

//    public abstract List<BookDto> mapToBookDtoList(List<Book> books);
}
