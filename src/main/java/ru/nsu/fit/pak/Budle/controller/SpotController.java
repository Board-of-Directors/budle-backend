package ru.nsu.fit.pak.Budle.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dto.SpotDto;
import ru.nsu.fit.pak.Budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.Budle.service.SpotServiceImpl;

import java.util.List;

@RestController
public class SpotController {
    @Autowired
    private SpotServiceImpl spotService;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @GetMapping(value = "/spot/{establishmentId}")
    public List<SpotDto> getSpotsByEstalibshment(@PathVariable("establishmentId") Long establishmentId){
        Establishment establishment = establishmentRepository.getEstablishmentById(establishmentId);
        return spotService.getSpotsByEstablishment(establishment);
    }
}
