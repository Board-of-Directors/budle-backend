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
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
@Slf4j
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private static final int DAY_COUNT_GENERATED_FOR_BOOKING = 14;
    private final ModelMapper mapper;
    private final WorkingHoursRepository workingHoursRepository;
    private final WorkingHoursMapper workingHoursMapper;

    @Override
    public void saveWorkingHours(Set<RequestWorkingHoursDto> responseWorkingHoursDto, Establishment establishment) {
        log.info("Saving working hours");
        Map<String, WorkingHours> workingHoursMap = new HashMap<>();
        for (RequestWorkingHoursDto dto : responseWorkingHoursDto) {
            log.info("Saving {}", dto);
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

    public List<ValidTimeDto> generateValidBookingHours(Establishment establishment) {
        List<ValidTimeDto> times = new ArrayList<>();
        Set<WorkingHours> workingHours = establishment.getWorkingHours();
        LocalDate currentDate = LocalDate.now();
        for (int dayNumber = 0; dayNumber < DAY_COUNT_GENERATED_FOR_BOOKING; dayNumber++) {
            LocalDate bookDate = currentDate.plusDays(dayNumber);
            ValidTimeDto currentDto = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(bookDate);
            Optional<WorkingHours> currentHours = workingHours.stream()
                .filter(x -> x.getDayOfWeek().getTranslateLittle().equals(currentDto.getDayName()))
                .findFirst();
            if (currentHours.isPresent()) {
                List<String> generatedTimes = generateTimeForDay(dayNumber, currentHours.get());
                if (!generatedTimes.isEmpty()) {
                    currentDto.setTimes(generatedTimes);
                    times.add(currentDto);
                }
            }
        }
        return times;
    }

    private List<String> generateTimeForDay(int dayNumber, WorkingHours currentHours) {
        final int DURATION = 30;
        List<LocalTime> generatedTimes;
        ZoneId zone = ZoneId.of("Asia/Novosibirsk");
        LocalTime now = LocalTime.from(ZonedDateTime.now(zone)).withSecond(0).withNano(0);
        if (dayNumber == 0 && now.isAfter(currentHours.getStartTime())) {
            generatedTimes = workingHoursMapper.generateTimes(
                now.plusMinutes(30 - (now.getMinute() % 30)),
                currentHours.getEndTime(),
                DURATION
            );
        } else {
            generatedTimes = workingHoursMapper.generateTimes(
                currentHours.getStartTime(),
                currentHours.getEndTime(),
                DURATION
            );
        }
        return generatedTimes.stream()
            .map(Objects::toString)
            .toList();
    }

    @Override
    public void deleteHours(Set<WorkingHours> workingHours) {
        workingHoursRepository.deleteAll(workingHours);
    }
}
