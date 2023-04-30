package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.request.RequestUserDto;

/**
 * Service that responsible for users.
 */
public interface UserService {

    /**
     * Function that register new user in our system.
     *
     * @param requestUserDto provide information about new user.
     */
    void registerUser(RequestUserDto requestUserDto);

    User findByPhoneNumber(String phoneNumber);

}
