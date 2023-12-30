package com.notesapp.notesapp.mapper.internal;

import com.notesapp.notesapp.dto.UserDto;
import com.notesapp.notesapp.mapper.UserMapper;
import com.notesapp.notesapp.model.User;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor(access = AccessLevel.PACKAGE)
public class UserMapperService implements UserMapper {
    @Override
    public User mapUserDtoToUser(UserDto userDto) {
        return User.builder()
                .userName(userDto.userName())
                .email(userDto.email())
                .password(userDto.password())
                .build();
    }
}
