package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.mapper.SpotMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.SpotRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SpotServiceImpl implements SpotService {
    private final SpotRepository spotRepository;

    private final EstablishmentRepository establishmentRepository;

    private final SpotMapper spotMapper;

    @Override
    public List<SpotDto> getSpotsByEstablishment(Long establishmentId) {
        Establishment establishment = establishmentRepository.getEstablishmentById(establishmentId);
        return spotMapper.ListModelToListDto(spotRepository.findByEstablishment(establishment));
    }

    public SpotDto getSpotById(Long spotId) {
        return spotMapper.modelToDto(spotRepository.findById(spotId)
                .orElseThrow());
    }

    public void createSpot(Long establishmentId) {
        spotRepository.save(new Spot(establishmentRepository.getReferenceById(establishmentId)));
    }
}
