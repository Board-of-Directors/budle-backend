package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dao.Spot;
import ru.nsu.fit.pak.Budle.dto.SpotDto;
import ru.nsu.fit.pak.Budle.repository.SpotRepository;

import java.util.List;

public class SpotService implements SpotServiceInterface{
    @Autowired
    SpotRepository spotRepository;
    @Override
    public List<SpotDto> getSpotsByEstablishment(Establishment establishment) {
        return spotRepository.findByEstablishment(establishment);
    }
}
