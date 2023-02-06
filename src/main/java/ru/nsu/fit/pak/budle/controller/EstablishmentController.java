package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.budle.dto.EstablishmentListDto;
import ru.nsu.fit.pak.budle.service.EstablishmentServiceImpl;

import javax.validation.Valid;


@RestController
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EstablishmentController {
    private final EstablishmentServiceImpl establishmentService;

    
    @GetMapping("/establishments")
    public EstablishmentListDto getEstablishments(@RequestParam(required = false) Category category,
                                                  @RequestParam(required = false) Boolean hasMap,
                                                  @RequestParam(required = false) Boolean hasCardPayment,
                                                  @RequestParam(required = false, defaultValue = "0") Integer offset,
                                                  @RequestParam(required = false, defaultValue = "100") Integer limit,
                                                  @RequestParam(required = false, defaultValue = "name") String sortValue) {
        EstablishmentListDto list = new EstablishmentListDto();
        list.setEstablishments(establishmentService.getEstablishmentByParams(category, hasMap, hasCardPayment,
                PageRequest.of(offset, limit, Sort.by(sortValue))));
        list.setCount(list.getEstablishments().size());
        return list;
    }

    @PostMapping("/establishment")
    public void createEstablishment(@Valid @RequestBody EstablishmentDto establishmentDto) {
        establishmentService.createEstablishment(establishmentDto);
    }


}