package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.UserDto;

public interface UserService {

    void registerUser(UserDto userDto);

   /* Boolean loginUser(UserDto userDto);

    Boolean existsPhoneNumber(String phoneNumber);

    */
}
