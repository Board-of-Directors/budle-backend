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
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final ModelMapper mapper;
    private final WorkingHoursRepository workingHoursRepository;

    private final WorkingHoursMapper workingHoursMapper;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void saveWorkingHours(Set<WorkingHoursDto> workingHoursDto, Establishment establishment) {
        logger.info("Saving working hours");
        for (WorkingHoursDto dto : workingHoursDto) {
            logger.debug("Saving " + dto);
            WorkingHours workingHours = mapper.map(dto, WorkingHours.class);
            workingHours.setDayOfWeek(DayOfWeek.getDayByString(dto.getDayOfWeek()));
            workingHours.setEstablishment(establishment);
            workingHoursRepository.save(workingHours);
        }
    }


    public List<ValidTimeDto> getValidBookingHoursByEstablishment(Establishment establishment) {
        List<ValidTimeDto> times = new ArrayList<>();
        Set<WorkingHours> workingHours = establishment.getWorkingHours();
        final int DAY_COUNT = 7;

        for (int i = 0; i < DAY_COUNT; i++) {
            LocalDate today = LocalDate.now()
                    .plusDays(i);
            ValidTimeDto currentDto = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(today);
            for (WorkingHours currentHours : workingHours) {
                if (currentHours.getDayOfWeek()
                        .getTranslateLittle()
                        .equals(currentDto.getDayName())) {

                    int extraMinutes = 30 - currentHours.getStartTime().getMinute() % 30;

                    List<String> currentDayList = new ArrayList<>();
                    for (LocalTime currentTime = currentHours.getStartTime().plusMinutes(extraMinutes);
                         currentTime.isBefore(currentHours.getEndTime());
                         currentTime = currentTime.plusMinutes(30)) {

                        currentDayList.add(currentTime.toString());
                    }

                    currentDto.setTimes(currentDayList);
                    times.add(currentDto);
                }

            }
        }
        return times;
    }
}
