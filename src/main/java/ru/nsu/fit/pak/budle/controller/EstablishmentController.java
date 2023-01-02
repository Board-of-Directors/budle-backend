package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.service.EstablishmentServiceImpl;

import java.util.List;


@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EstablishmentController {
    private final EstablishmentServiceImpl establishmentService;


    @GetMapping("/establishments")
    public List<EstablishmentDto> getEstablishments(@RequestParam(required = false) String category,
                                                    @RequestParam(required = false) Boolean hasMap,
                                                    @RequestParam(required = false) Boolean hasCardPayment) {
        return establishmentService.getEstablishmentByParams(category, hasMap, hasCardPayment);
    }


}