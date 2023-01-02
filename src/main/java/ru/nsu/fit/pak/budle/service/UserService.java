package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.UserDto;

import java.util.List;

public interface UserService {

    List<UserDto> getUsers();

    Boolean registerUser(UserDto userDto);

    Boolean loginUser(UserDto userDto);

    Boolean existsPhoneNumber(String phoneNumber);
}
