package ru.nsu.fit.pak.Budle.service;

import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.UserDto;

import java.util.List;

public interface UserServiceInterface {
    User registerUser(UserDto userDto);

    List<UserDto> getUsers();

    User loginUser(UserDto userDto);
}
