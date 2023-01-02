package ru.nsu.fit.pak.budle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.service.SpotServiceImpl;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SpotController {
    private final SpotServiceImpl spotService;

    @GetMapping(value = "/spot")
    public List<SpotDto> getSpotsByEstablishment(@RequestParam Long establishmentId) {
        return spotService.getSpotsByEstablishment(establishmentId);
    }
}
