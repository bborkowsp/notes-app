package com.notesapp.notesapp.service.internal;

import com.notesapp.notesapp.dto.UserDto;
import com.notesapp.notesapp.mapper.UserMapper;
import com.notesapp.notesapp.repository.UserRepository;
import com.notesapp.notesapp.service.UserUseCases;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService implements UserUseCases {

    private final UserMapper userMapper;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public void createUser(UserDto userDto) {
        final var user = userMapper.mapUserDtoToUser(userDto);
        userRepository.save(user);
    }
}
