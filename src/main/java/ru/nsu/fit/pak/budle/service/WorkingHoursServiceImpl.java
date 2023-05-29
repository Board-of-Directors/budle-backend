package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.DayOfWeek;
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestWorkingHoursDto;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final ModelMapper mapper;
    private final WorkingHoursRepository workingHoursRepository;

    private final WorkingHoursMapper workingHoursMapper;

    @Override
    public void saveWorkingHours(Set<RequestWorkingHoursDto> responseWorkingHoursDto, Establishment establishment) {
        log.info("Saving working hours");
        Map<String, WorkingHours> workingHoursMap = new HashMap<>();
        for (RequestWorkingHoursDto dto : responseWorkingHoursDto) {
            log.info("Saving " + dto);
            for (String day : dto.getDays()) {
                WorkingHours workingHours = mapper.map(dto, WorkingHours.class);
                workingHours.setDayOfWeek(DayOfWeek.getDayByString(day));
                workingHours.setEstablishment(establishment);
                workingHoursMap.put(
                        workingHours.getDayOfWeek().getTranslate(),
                        workingHours
                );
            }
        }
        workingHoursRepository.saveAll(workingHoursMap.values());
    }


    public List<ValidTimeDto> getValidBookingHoursByEstablishment(Establishment establishment) {
        List<ValidTimeDto> times = new ArrayList<>();
        Set<WorkingHours> workingHours = establishment.getWorkingHours();

        final int DAY_COUNT = 14;
        for (int i = 0; i < DAY_COUNT; i++) {
            LocalDate today = LocalDate.now().plusDays(i);
            ValidTimeDto currentDto = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(today);
            Optional<WorkingHours> currentHours = workingHours
                    .stream()
                    .filter(x -> x.getDayOfWeek().getTranslateLittle().equals(currentDto.getDayName()))
                    .findFirst();
            if (currentHours.isPresent()) {
                final int DURATION = 30;
                List<LocalTime> generatedTimes;
                if (i == 0) {
                    LocalTime now = LocalTime.now();
                    generatedTimes = workingHoursMapper.generateTimes(
                            now.plusMinutes(30 - (now.getMinute() % 30)),
                            currentHours.get().getEndTime(),
                            DURATION
                    );
                } else {
                    generatedTimes = workingHoursMapper.generateTimes(
                            currentHours.get().getStartTime(),
                            currentHours.get().getEndTime(),
                            DURATION
                    );
                }
                List<String> currentDayTimes = generatedTimes
                        .stream()
                        .map(Objects::toString)
                        .toList();

                if (!currentDayTimes.isEmpty()) {
                    currentDto.setTimes(currentDayTimes);
                    times.add(currentDto);
                }
            }
        }
        return times;
    }

    @Override
    public void deleteHours(Set<WorkingHours> workingHours) {
        workingHoursRepository.deleteAll(workingHours);

    }
}
