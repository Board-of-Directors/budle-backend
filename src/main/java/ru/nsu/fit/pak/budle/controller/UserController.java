package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.ShortEstablishmentInfo;
import ru.nsu.fit.pak.budle.dto.UserDto;
import ru.nsu.fit.pak.budle.service.EstablishmentService;
import ru.nsu.fit.pak.budle.service.SecurityService;
import ru.nsu.fit.pak.budle.service.UserServiceImpl;

import javax.validation.Valid;
import java.util.List;

/**
 * Class, that represent user controller.
 * main endpoint = "API:PORT/user"
 */
@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@RequestMapping(value = "user", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {
    private final UserServiceImpl userService;

    private final EstablishmentService establishmentService;

    private final SecurityService securityService;


    /**
     * Post request, that takes user information and add it to our database.
     *
     * @param userDto - information about new user.
     * @return true - if operation was success, false - otherwise
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean register(@RequestBody @Valid UserDto userDto) {
        userService.registerUser(userDto);
        securityService.autoLogin(userDto.getUsername(), userDto.getPassword());
        return true;
    }

    /**
     * Post request, that authorize user in our system.
     *
     * @param userDto information about user credentials.
     * @return true - if operation was success, false - otherwise.
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean login(@RequestBody UserDto userDto) {
        securityService.autoLogin(userDto.getUsername(), userDto.getPassword());
        return true;
    }

    @GetMapping(value = "/establishments")
    public List<ShortEstablishmentInfo> OwnerEstablishments(@RequestParam Long id) {
        return establishmentService.getEstablishmentsByOwner(id);
    }

}