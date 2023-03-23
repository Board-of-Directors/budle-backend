package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public List<SpotDto> getSpotsByEstablishment(Long establishmentId) {
        logger.info("Getting spots by establishment");
        logger.debug("EstablishmentID: " + establishmentId);
        Establishment establishment = establishmentRepository.getEstablishmentById(establishmentId);
        return spotMapper.ListModelToListDto(spotRepository.findByEstablishment(establishment));
    }

    @Override
    public SpotDto getSpotById(Long spotId) {
        logger.info("Getting spot by id");
        logger.debug("SpotID: " + spotId);
        return spotMapper.modelToDto(spotRepository.findById(spotId)
                .orElseThrow());
    }

    @Override
    public void createSpot(Long localId, Long establishmentId) {
        logger.info("Saving new spot");
        logger.debug("LocalID: " + localId);
        logger.debug("EstablishmentID: " + establishmentId);
        spotRepository.save(
                new Spot(localId, establishmentRepository.getReferenceById(establishmentId))
        );
    }
}
