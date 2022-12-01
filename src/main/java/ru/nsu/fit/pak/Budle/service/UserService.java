package ru.nsu.fit.pak.Budle.service;

import ru.nsu.fit.pak.Budle.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    Boolean registerUser(UserDto userDto);

    Boolean loginUser(UserDto userDto);

    Boolean existsPhoneNumber(String phoneNumber);
}
