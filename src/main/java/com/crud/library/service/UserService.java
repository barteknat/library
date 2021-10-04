package com.crud.library.service;

import com.crud.library.dto.UserDto;
import com.crud.library.exception.AlreadyExistsExeption;
import com.crud.library.mapper.UserMapper;
import com.crud.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository repository;
    private final UserMapper mapper;

    @Transactional
    public UserDto createUser(UserDto userDto) throws AlreadyExistsExeption {
        if (isExist(userDto)) throw new AlreadyExistsExeption("USER ALREADY EXISTS IN DATABASE");
        return mapper.mapToUserDto(repository.save(mapper.mapToUser(userDto)));
    }

    private boolean isExist(UserDto userDto) {
        if (repository.existsByMailAddress(userDto.getMailAddress())) {
            log.error("USER ALREADY EXISTS");
            return true;
        }
        return false;
    }
}
