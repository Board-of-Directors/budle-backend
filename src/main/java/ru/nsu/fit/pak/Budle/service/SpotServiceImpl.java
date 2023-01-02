package ru.nsu.fit.pak.Budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dto.SpotDto;
import ru.nsu.fit.pak.Budle.mapper.SpotMapper;
import ru.nsu.fit.pak.Budle.repository.SpotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    private final SpotRepository spotRepository;

    private final SpotMapper spotMapper;

    @Override
    public List<SpotDto> getSpotsByEstablishment(Establishment establishment) {
        return spotMapper.ListModelToListDto(spotRepository.findByEstablishment(establishment));
    }
}
