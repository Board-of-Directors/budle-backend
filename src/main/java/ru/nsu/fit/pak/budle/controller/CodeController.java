package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.request.RequestCodeDto;
import ru.nsu.fit.pak.budle.service.CodeService;

/**
 * Class that represents code controller.
 * Main aim of this controller - creating user codes and checking user codes.
 */
@RestController
@RequestMapping(value = "code", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
@CrossOrigin
public class CodeController {
    private final CodeService codeService;
    /**
     * Get request, that creates code for user registration.
     *
     * @param phoneNumber of user, who wants to register his account.
     * @return boolean variable, that indicates success of this request.
     */
    // TODO: GET -> POST
    @GetMapping
    public Boolean getCode(@RequestParam String phoneNumber) {
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
    public Boolean checkCode(@RequestBody RequestCodeDto codeDto) {
        return codeService.checkCode(codeDto.getPhoneNumber(), codeDto.getCode());
    }
}
