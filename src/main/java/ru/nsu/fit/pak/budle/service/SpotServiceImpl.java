package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Establishment;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.mapper.SpotMapper;
import ru.nsu.fit.pak.budle.repository.SpotRepository;

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
