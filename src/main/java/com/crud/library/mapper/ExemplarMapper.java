package com.crud.library.mapper;

import com.crud.library.domain.Exemplar;
import com.crud.library.dto.ExemplarDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ExemplarMapper {

    public abstract Exemplar mapToExemplar(ExemplarDto exemplarDto);

    public abstract ExemplarDto mapToExemplarDto(Exemplar exemplar);
}
