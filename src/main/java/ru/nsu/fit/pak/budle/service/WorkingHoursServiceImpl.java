package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.DayOfWeek;
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestWorkingHoursDto;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;

import java.time.LocalDate;
import java.util.*;

@Service
@RequiredArgsConstructor
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final ModelMapper mapper;
    private final WorkingHoursRepository workingHoursRepository;

    private final WorkingHoursMapper workingHoursMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void saveWorkingHours(Set<RequestWorkingHoursDto> responseWorkingHoursDto, Establishment establishment) {
        logger.info("Saving working hours");
        for (RequestWorkingHoursDto dto : responseWorkingHoursDto) {
            logger.debug("Saving " + dto);
            for (String day : dto.getDays()) {
                WorkingHours workingHours = mapper.map(dto, WorkingHours.class);
                workingHours.setDayOfWeek(DayOfWeek.getDayByString(day));
                workingHours.setEstablishment(establishment);
                workingHoursRepository.save(workingHours);
            }
        }
    }


    public List<ValidTimeDto> getValidBookingHoursByEstablishment(Establishment establishment) {
        List<ValidTimeDto> times = new ArrayList<>();
        Set<WorkingHours> workingHours = establishment.getWorkingHours();

        final int DAY_COUNT = 7;
        for (int i = 0; i < DAY_COUNT; i++) {
            LocalDate today = LocalDate.now().plusDays(i);
            ValidTimeDto currentDto = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(today);
            Optional<WorkingHours> currentHours = workingHours
                    .stream()
                    .filter(x -> x.getDayOfWeek().getTranslateLittle().equals(currentDto.getDayName()))
                    .findFirst();
            if (currentHours.isPresent()) {
                final int DURATION = 30;
                List<String> currentDayTimes = workingHoursMapper.generateTimes(
                                currentHours.get().getStartTime(),
                                currentHours.get().getEndTime(),
                                DURATION
                        )
                        .stream()
                        .map(Objects::toString)
                        .toList();
                currentDto.setTimes(currentDayTimes);
                times.add(currentDto);
            }
        }
        return times;
    }
}
