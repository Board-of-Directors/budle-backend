package ru.nsu.fit.pak.Budle.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dao.Spot;
import ru.nsu.fit.pak.Budle.dto.SpotDto;
import ru.nsu.fit.pak.Budle.mapper.SpotMapper;
import ru.nsu.fit.pak.Budle.repository.SpotRepository;

import java.util.List;

@Service
public class SpotService implements SpotServiceInterface{
    @Autowired
    private SpotRepository spotRepository;

    @Autowired
    private SpotMapper spotMapper;
    @Override
    public List<SpotDto> getSpotsByEstablishment(Establishment establishment) {
        return spotMapper.ListModelToListDto(spotRepository.findByEstablishment(establishment));
    }
}
