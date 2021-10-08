package com.crud.library.mapper;

import com.crud.library.domain.User;
import com.crud.library.dto.UserDto;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User mapToUser(UserDto userDto);

    public abstract UserDto mapToUserDto(User user);
}
