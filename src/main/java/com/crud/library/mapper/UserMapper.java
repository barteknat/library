package com.crud.library.mapper;

import com.crud.library.domain.User;
import com.crud.library.dto.UserDto;
import org.mapstruct.Mapper;

//import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    public abstract User mapToUser(UserDto userDto);

    public abstract UserDto mapToUserDto(User user);

//    public abstract List<UserDto> userDtoList(List<User> users);
}
