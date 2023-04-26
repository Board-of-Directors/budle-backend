package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
import ru.nsu.fit.pak.budle.repository.SpotRepository;

import java.time.LocalDate;
import java.time.format.TextStyle;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.stream.Collectors;

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
                .orElseThrow(() -> new SpotNotFoundException(spotId)));
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

    @Override
    public TimelineDto getSpotTimeline(Long localId, Long establishmentId) {
        logger.info("Getting spot timeline");
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));
        Spot spot = spotRepository.findByEstablishmentAndLocalId(establishment, localId)
                .orElseThrow(() -> new SpotNotFoundException(localId));
        Set<WorkingHours> workingHours = spot.getEstablishment().getWorkingHours();
        LocalDate dateNow = LocalDate.now();
        String today = dateNow.getDayOfWeek().getDisplayName(TextStyle.SHORT,
                new Locale("ru"));
        WorkingHours todayHours = workingHours.stream()
                .filter(x -> x.getDayOfWeek()
                        .getTranslateLittle()
                        .equals(today))
                .findFirst()
                .orElseThrow(() -> new SpotNotFoundException(localId));


        TimelineDto timelineDto = new TimelineDto();
        timelineDto.setStart(todayHours.getStartTime());
        timelineDto.setEnd(todayHours.getEndTime());
        Set<BookingTimesDto> times = establishmentRepository
                .findWorkingHoursByDay(DayOfWeek.getDayByOrdinal(dateNow.getDayOfWeek().getValue()))
                .stream()
                .map(x -> new BookingTimesDto(x.getStartTime().toString(), x.getEndTime().toString()))
                .collect(Collectors.toSet());

       /* Set<BookingTimesDto> times = spot.getEstablishment()
                .getOrders()
                .stream()
                .filter(x -> x.getDate().getDay() == dateNow.getDayOfWeek().getValue() % 7)
                .sorted((o1, o2) -> {
                    if (o1.getStartTime().toLocalTime().isBefore(o2.getStartTime().toLocalTime())) {
                        return 1;
                    } else if (o1.equals(o2)) {
                        return 0;
                    } else {
                        return -1;
                    }
                })
                .map(x -> new BookingTimesDto(x.getStartTime().toString(), x.getEndTime().toString()))
                .collect(Collectors.toSet());

        */
        timelineDto.setTimes(times);
        return timelineDto;

    }
}
