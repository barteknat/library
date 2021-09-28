package com.crud.library.mapper;

import com.crud.library.domain.Rental;
import com.crud.library.dto.RentalDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class RentalMapper {

    public abstract Rental mapToRental(RentalDto rentalDto);

    public abstract RentalDto mapToRentalDto(Rental rental);
}
