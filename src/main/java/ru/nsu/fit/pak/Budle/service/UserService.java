package ru.nsu.fit.pak.Budle.service;

import ru.nsu.fit.pak.Budle.dto.UserDto;

import java.util.List;

public interface UserService {
    UserDto registerUser(UserDto userDto);

    List<UserDto> getUsers();

    UserDto loginUser(UserDto userDto);
}
