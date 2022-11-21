package ru.nsu.fit.pak.Budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.Budle.Exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.UserDto;
import ru.nsu.fit.pak.Budle.service.UserService;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class UserController {
    @Autowired
    UserService userService;


    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST, e.getMessage(), e);

        }

    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            User registered = userService.registerUser(userDto);
            String answer = registered.getFirstName() + " ,you was registered";
            return new ResponseEntity<>(answer, HttpStatus.OK);

        } catch (UserAlreadyExistsException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>("You was not registered because of some problem", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login", consumes = {"application/json"})
    public ResponseEntity<String> login(@RequestBody UserDto userDto) {
        try {
            User logged = userService.loginUser(userDto);
            if (logged != null) {
                String answer = logged.getFirstName() + ", you was authorized";
                return new ResponseEntity<>(answer, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Your phone number or password is not correct.", HttpStatus.BAD_REQUEST);
            }
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

}