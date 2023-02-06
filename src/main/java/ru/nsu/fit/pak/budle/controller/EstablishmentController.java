package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.service.EstablishmentServiceImpl;

import javax.validation.Valid;
import java.util.List;


@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EstablishmentController {
    private final EstablishmentServiceImpl establishmentService;


    // TODO: PAGINATION
    @GetMapping("/establishments")
    public List<EstablishmentDto> getEstablishments(@RequestParam(required = false) String category,
                                                    @RequestParam(required = false) Boolean hasMap,
                                                    @RequestParam(required = false) Boolean hasCardPayment,
                                                    @RequestParam(required = false) Integer offset,
                                                    @RequestParam(required = false) Integer limit) {
        return establishmentService.getEstablishmentByParams(category, hasMap, hasCardPayment);
    }

    @PostMapping("/establishment")
    public void createEstablishment(@Valid @RequestBody EstablishmentDto establishmentDto) {
        establishmentService.createEstablishment(establishmentDto);
    }


}