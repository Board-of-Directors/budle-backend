package ru.nsu.fit.pak.Budle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ru.nsu.fit.pak.Budle.service.UserService;
import ru.nsu.fit.pak.Budle.dto.UserDto;
import ru.nsu.fit.pak.Budle.dao.User;

@RestController
public class RouteController {
    @Autowired
    UserService userService;

    public RouteController() {
    }


    @GetMapping("/users")
    public ResponseEntity<String> getUsers() {
        try {
            return new ResponseEntity<>(userService.getUsers().toString(), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

    }

    @PostMapping(value = "/register", consumes = {"application/json"})
    public ResponseEntity<String> register(@RequestBody UserDto userDto) {
        try {
            User registered = userService.registerUser(userDto);
            String answer = registered.getFirstName() + " ,you was registered";
            return new ResponseEntity<>(answer, HttpStatus.OK);

        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);

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