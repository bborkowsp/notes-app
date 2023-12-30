package com.notesapp.notesapp.mapper;

import com.notesapp.notesapp.dto.UserDto;
import com.notesapp.notesapp.model.User;

public interface UserMapper {
    User mapUserDtoToUser(UserDto userDto);
}
