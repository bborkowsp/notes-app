package com.notesapp.notesapp.mapper.internal;

import com.notesapp.notesapp.dto.UserDto;
import com.notesapp.notesapp.mapper.UserMapper;
import com.notesapp.notesapp.model.User;
import org.springframework.stereotype.Component;

@Component
class UserMapperService implements UserMapper {
    @Override
    public User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .username(userDto.username())
                .email(userDto.email())
                .password(userDto.password())
                .build();
    }
}
