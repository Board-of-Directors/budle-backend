package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.DayOfWeek;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.BookingTimesDto;
import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.dto.TimelineDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.exceptions.SpotNotFoundException;
import ru.nsu.fit.pak.budle.mapper.SpotMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.SpotRepository;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class SpotServiceImpl implements SpotService {
    private final SpotRepository spotRepository;
    private final EstablishmentRepository establishmentRepository;
    private final SpotMapper spotMapper;
    private final OrderRepository orderRepository;

    @Override
    public List<SpotDto> getSpotsByEstablishment(Long establishmentId) {
        log.info("Getting spots by establishment");
        log.info("EstablishmentID: " + establishmentId);
        Establishment establishment = establishmentRepository.getEstablishmentById(establishmentId);
        return spotMapper.ListModelToListDto(spotRepository.findByEstablishment(establishment));
    }

    @Override
    public SpotDto getSpotById(Long spotId) {
        log.info("Getting spot by id");
        log.info("SpotID: " + spotId);
        return spotMapper.modelToDto(spotRepository.findById(spotId)
            .orElseThrow(() -> new SpotNotFoundException(spotId)));
    }

    @Override
    public void createSpot(Long localId, Long establishmentId) {
        log.info("Saving new spot");
        log.info("LocalID: " + localId);
        log.info("EstablishmentID: " + establishmentId);
        spotRepository.save(
            new Spot(localId, establishmentRepository.getReferenceById(establishmentId))
        );
    }

    @Override
    public void saveSpots(List<Spot> spots, Long establishmentId) {
        spots = spots.stream()
            .peek(spot -> spot.setEstablishment(establishmentRepository.getReferenceById(establishmentId)))
            .toList();
        spotRepository.saveAll(spots);
    }

    @Override
    public TimelineDto getSpotTimeline(Long localId, Long establishmentId) {
        log.info("Getting spot timeline");
        Establishment establishment = establishmentRepository.findById(establishmentId)
            .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        Spot spot = spotRepository.findByEstablishmentAndLocalId(establishment, localId)
            .orElseThrow(() -> new SpotNotFoundException(localId));

        LocalDate dateNow = LocalDate.now();
        String today = dateNow.getDayOfWeek().getDisplayName(
            TextStyle.SHORT,
            new Locale("ru")
        );

        WorkingHours todayHours = establishmentRepository
            .findWorkingHoursByDay(DayOfWeek.getDayByLittleString(today));

        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setStart(todayHours.getStartTime());
        timelineDto.setEnd(todayHours.getEndTime());
        Set<BookingTimesDto> times = orderRepository.findAllByDateAndEstablishment(
                Date.valueOf(dateNow),
                spot.getEstablishment()
            )
            .stream()
            .map(x -> new BookingTimesDto(x.getStartTime().toString(), x.getEndTime().toString()))
            .collect(Collectors.toSet());
        timelineDto.setTimes(times);
        return timelineDto;

    }
}
