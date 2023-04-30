package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.request.RequestUserDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo.ResponseShortEstablishmentInfo;
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
     * @param requestUserDto - information about new user.
     * @return true - if operation was success, false - otherwise
     */
    @PostMapping(value = "/register", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean register(@RequestBody @Valid RequestUserDto requestUserDto) {
        userService.registerUser(requestUserDto);
        securityService.autoLogin(requestUserDto.getUsername(), requestUserDto.getPassword());
        return true;
    }

    /**
     * Post request, that authorize user in our system.
     *
     * @param requestUserDto information about user credentials.
     * @return true - if operation was success, false - otherwise.
     */
    @PostMapping(value = "/login", consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean login(@RequestBody RequestUserDto requestUserDto) {
        securityService.autoLogin(requestUserDto.getUsername(), requestUserDto.getPassword());
        return true;
    }

    @GetMapping(value = "/establishments")
    public List<ResponseShortEstablishmentInfo> OwnerEstablishments(@RequestParam Long id) {
        return establishmentService.getEstablishmentsByOwner(id);
    }

}