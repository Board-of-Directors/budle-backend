package ru.nsu.fit.pak.Budle.service;

import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dao.Spot;
import ru.nsu.fit.pak.Budle.dto.SpotDto;

import java.util.List;

public interface SpotServiceInterface {
    List<SpotDto> getSpotsByEstablishment(Establishment establishment);
}
