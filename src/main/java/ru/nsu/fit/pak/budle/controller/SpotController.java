package ru.nsu.fit.pak.budle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.service.SpotServiceImpl;

@RestController
@RequestMapping(value = "/spot", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class SpotController {
    private final SpotServiceImpl spotService;

    @GetMapping()
    public SpotDto get(@RequestParam Long spotId) {
        return spotService.getSpotById(spotId);
    }
}
