package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.CodeDto;
import ru.nsu.fit.pak.budle.service.CodeService;

import java.io.IOException;

/**
 * Class that represents code controller.
 * Main aim of this controller - creating user codes and checking user codes.
 */
@RestController
@RequestMapping(value = "code",
        produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CodeController {


    private final CodeService codeService;

    /**
     * Get request, that creates code for user registration.
     *
     * @param phoneNumber of user, who wants to register his account.
     * @return boolean variable, that indicates success of this request.
     * @throws IOException - when it was some exception
     */
    // TODO: GET -> POST
    // FIXME: delete IOException
    @GetMapping
    public Boolean getCode(@RequestParam String phoneNumber) throws IOException {
        return codeService.generateCode(phoneNumber);
    }

    // TODO: VALID Code Dto

    /**
     * Post request, that validate existing of registration code in our database.
     *
     * @param codeDto contains phoneNumber and code, that we need to check
     * @return true - if code was in database, false - if there is no such code
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Boolean checkCode(@RequestBody CodeDto codeDto) {
        return codeService.checkCode(codeDto.getPhoneNumber(), codeDto.getCode());
    }
}
