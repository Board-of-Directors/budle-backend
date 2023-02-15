package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.UserDto;
import ru.nsu.fit.pak.budle.exceptions.IncorrectDataException;
import ru.nsu.fit.pak.budle.exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.budle.mapper.UserMapper;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;

    // TODO: Хеширование пароля, Security
    @Override
    public Boolean registerUser(UserDto userDto) {

        if (userRepository.existsByPhoneNumber(userDto.getPhoneNumber())) {
            throw new UserAlreadyExistsException("Пользователь с таким номером уже существует.");
        } else {
            User user = userMapper.dtoToModel(userDto);
            userRepository.save(user);
            return true;
        }
    }

    @Override
    public List<UserDto> getUsers() {
        return userMapper.modelListToDtoList(userRepository.findAll());
    }

    //TODO: Совместить два исключения в одно
    @Override
    public Boolean loginUser(UserDto userDto) {
        try {
            User user = userRepository.findByPhoneNumber(userDto.getPhoneNumber()).orElseThrow();

            if (user.getPassword().equals(userDto.getPassword())) {
                return true;
            } else {
                throw new IncorrectDataException("Номер или пароль введены неправильно.");
            }
        } catch (NoSuchElementException e) {
            throw new IncorrectDataException("Пользователя с такими данными не существует.");
        }
    }

    @Override
    public Boolean existsPhoneNumber(String phoneNumber) {
        return !userRepository.existsByPhoneNumber(phoneNumber);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}