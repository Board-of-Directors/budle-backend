package ru.nsu.fit.pak.budle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.service.SpotServiceImpl;

@RestController
@RequestMapping(value = "/spot", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SpotController {
    private final SpotServiceImpl spotService;

    @PostMapping
    public SpotDto getSpotByOrder(@RequestBody OrderDto order) {
        return null;
    }
}
