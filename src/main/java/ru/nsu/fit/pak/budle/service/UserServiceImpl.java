package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.request.RequestUserDto;
import ru.nsu.fit.pak.budle.exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.UserNotFoundException;
import ru.nsu.fit.pak.budle.mapper.UserMapper;
import ru.nsu.fit.pak.budle.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService, UserDetailsService {


    private final UserRepository userRepository;

    private final UserMapper userMapper;
    private final PasswordEncoder encoder;

    @Override
    public void registerUser(RequestUserDto requestUserDto) {

        if (userRepository.existsByPhoneNumber(requestUserDto.getPhoneNumber()) || userRepository.existsByUsername(requestUserDto.getUsername())) {
            throw new UserAlreadyExistsException();
        } else {
            User user = userMapper.dtoToModel(requestUserDto);
            user.setPassword(encoder.encode(user.getPassword()));
            userRepository.save(user);
        }
    }

    @Override
    public User findByPhoneNumber(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).orElseThrow(UserNotFoundException::new);
    }


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }
}