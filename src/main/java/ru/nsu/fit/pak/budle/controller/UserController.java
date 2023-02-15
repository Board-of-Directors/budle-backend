package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.UserDto;
import ru.nsu.fit.pak.budle.service.SecurityService;
import ru.nsu.fit.pak.budle.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserServiceImpl userService;

    private final SecurityService securityService;


    @GetMapping
    public List<UserDto> getUsers() {
        return userService.getUsers();
    }

    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean register(@RequestBody @Valid UserDto userDto) {
        userService.registerUser(userDto);
        securityService.autoLogin(userDto.getUsername(), userDto.getPassword());
        return true;
    }

    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean login(@RequestBody UserDto userDto) {
        securityService.autoLogin(userDto.getUsername(), userDto.getPassword());
        return true;
    }

}