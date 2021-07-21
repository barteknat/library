package com.crud.library.mapper;

import com.crud.library.domain.Lend;
import com.crud.library.dto.LendDto;
import org.mapstruct.Mapper;

//import java.util.List;

@Mapper(componentModel = "spring")
public abstract class LendMapper {

    public abstract Lend mapToLend(LendDto lendDto);

    public abstract LendDto mapToLendDto(Lend lend);

//    public abstract List<LendDto> mapToLendDtoList(List<Lend> lends);
}
