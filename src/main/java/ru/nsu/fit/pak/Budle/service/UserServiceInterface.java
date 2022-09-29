package ru.nsu.fit.pak.Budle.service;

import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.UserDto;

public interface UserServiceInterface {
    User registerUser(UserDto userDto);

    Iterable<UserDto> getUsers();

    User loginUser(UserDto userDto);
}
