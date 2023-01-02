package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.UserDto;
import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;
import ru.nsu.fit.pak.budle.exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.budle.mapper.UserMapper;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Override
    public Boolean registerUser(UserDto userDto) {

        if (!userRepository.findByPhoneNumber(userDto.getPhoneNumber()).isEmpty()) {
            throw new UserAlreadyExistsException("Пользователь с таким номером уже существует.");
        } else {
            User user = userMapper.dtoToUser(userDto);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public List<UserDto> getUsers() {
        return userMapper.modelListToDtoList(userRepository.findAll());
    }

    @Override
    public Boolean loginUser(UserDto userDto) {
        User user;
        try {
            user = userRepository.findByPhoneNumber(userDto.getPhoneNumber()).get(0);
        } catch (Exception e) {
            throw new IncorrectDataException("Пользователя с такими данными не существует.");
        }
        if (Objects.equals(user.getPass(), userDto.getPassword())) {
            return true;
        } else {
            throw new IncorrectDataException("Номер или пароль введены неправильно.");
        }
    }

    @Override
    public Boolean existsPhoneNumber(String phoneNumber) {
        return !userRepository.findByPhoneNumber(phoneNumber).isEmpty();
    }
}