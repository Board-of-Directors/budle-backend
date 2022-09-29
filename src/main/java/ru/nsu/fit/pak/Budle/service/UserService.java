package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ru.nsu.fit.pak.Budle.repository.UserRepository;
import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.UserDto;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;


    @Override
    public User registerUser(UserDto userDto) {
        if (!userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isEmpty()) {
            throw new IllegalArgumentException("This phone number already exists in our system. ");
        } else {
            User user = new User();
            user.setFirstName(userDto.getFirstName());
            user.setLastName(userDto.getLastName());
            user.setPhoneNumber(userDto.getPhoneNumber());
            user.setPass(userDto.getPassword());
            userRepository.save(user);
            return user;
        }
    }

    @Override
    public Iterable<UserDto> getUsers() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto dto = new UserDto();
            dto.setId(user.getId());
            dto.setFirstName(user.getFirstName());
            dto.setLastName(user.getLastName());
            dto.setPhoneNumber(user.getPhoneNumber());
            dto.setPassword(user.getPass());
            userDtos.add(dto);
        }
        return userDtos;
    }

    @Override
    public User loginUser(UserDto userDto) {
        try {
            User user = userRepository.findByPhoneNumber(userDto.getPhoneNumber()).get(0);

            if (Objects.equals(user.getPass(), userDto.getPassword())) {
                return user;
            } else {
                return null;
            }
        } catch (Exception e) {
            throw new RuntimeException("User with such phone number does not exist");
        }
    }
}