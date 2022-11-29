package ru.nsu.fit.pak.Budle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.Budle.dto.CodeDto;
import ru.nsu.fit.pak.Budle.service.CodeService;

import java.io.IOException;

@RestController
public class CodeController {

    @Autowired
    CodeService codeService;

    @GetMapping(value = "/getCode", produces = MediaType.APPLICATION_JSON_VALUE)
    public void getCode(@RequestParam String phoneNumber) throws IOException {
        codeService.generateCode(phoneNumber);
    }

    @PostMapping(value = "/checkCode", consumes = "application/json", produces = MediaType.APPLICATION_JSON_VALUE)
    public Boolean checkCode(@RequestBody CodeDto codeDto) {
        return codeService.checkCode(codeDto.getPhoneNumber(), codeDto.getCode());
    }
}
