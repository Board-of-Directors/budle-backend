package ru.nsu.fit.pak.Budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;
import ru.nsu.fit.pak.Budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.Budle.service.EstablishmentService;

import java.util.List;


@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EstablishmentController {
    @Autowired
    EstablishmentService establishmentService;


    @GetMapping("/establishments")
    public List<EstablishmentDto> getEstablishments() {
        return establishmentService.getEstablishments();
    }

}