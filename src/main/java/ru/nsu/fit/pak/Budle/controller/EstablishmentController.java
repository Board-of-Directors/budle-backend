package ru.nsu.fit.pak.Budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.Budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.Budle.service.EstablishmentServiceImpl;

import java.util.List;


@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EstablishmentController {
    @Autowired
    private EstablishmentServiceImpl establishmentService;


    @GetMapping("/establishments")
    public List<EstablishmentDto> getEstablishments(@RequestParam String category) {
        if (category != null){
            return establishmentService.getEstablishmentsByCategory(category);
        }
        return establishmentService.getEstablishments();
    }



}