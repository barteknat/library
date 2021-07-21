package com.crud.library.mapper;

import com.crud.library.domain.BookItem;
import com.crud.library.dto.BookItemDto;
import org.mapstruct.Mapper;

//import java.util.List;

@Mapper(componentModel = "spring")
public abstract class BookItemMapper {

    public abstract BookItem mapToBookItem(BookItemDto bookItemDto);

    public abstract BookItemDto mapToBookItemDto(BookItem bookItem);

//    public abstract List<BookItemDto> mapToBookItemDtoList(List<BookItem> bookItems);

}
