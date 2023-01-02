package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.SpotDto;

import java.util.List;

public interface SpotService {
    List<SpotDto> getSpotsByEstablishment(Long establishmentId);
}
