package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.UserDto;

/**
 * Service that responsible for users.
 */
public interface UserService {

    /**
     * Function that register new user in our system.
     *
     * @param userDto provide information about new user.
     */
    void registerUser(UserDto userDto);

    User findByPhoneNumber(String phoneNumber);

}
