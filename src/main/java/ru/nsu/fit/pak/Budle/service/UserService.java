package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.Exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.UserDto;
import ru.nsu.fit.pak.Budle.mapper.UserMapper;
import ru.nsu.fit.pak.Budle.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
public class UserService implements UserServiceInterface {

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserMapper userMapper;

    @Override
    public User registerUser(UserDto userDto) {

        if (!userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isEmpty()) {
            throw new UserAlreadyExistsException("This phone number already exists in our system. ");
        } else {
            User user = userMapper.dtoToUser(userDto);
            userRepository.save(user);
            return user;
        }
    }

    @Override
    public List<UserDto> getUsers() {
        Iterable<User> users = userRepository.findAll();
        ArrayList<UserDto> userDtos = new ArrayList<>();
        for (User user : users) {
            UserDto dto = userMapper.modelToDto(user);
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