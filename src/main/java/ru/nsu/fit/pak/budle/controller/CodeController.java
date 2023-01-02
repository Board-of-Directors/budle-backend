package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.CodeDto;
import ru.nsu.fit.pak.budle.service.CodeService;

import java.io.IOException;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class CodeController {


    private final CodeService codeService;

    @GetMapping(value = "/getCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean getCode(@RequestParam String phoneNumber) throws IOException {
        return codeService.generateCode(phoneNumber);
    }

    @PostMapping(value = "/checkCode", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean checkCode(@RequestBody CodeDto codeDto) {
        return codeService.checkCode(codeDto.getPhoneNumber(), codeDto.getCode());
    }
}
