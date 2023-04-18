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
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkingHoursServiceImpl implements WorkingHoursService {
    private final ModelMapper mapper;
    private final WorkingHoursRepository workingHoursRepository;

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
        int dayCountInOneWeek = 7;

        for (int i = 0; i < dayCountInOneWeek; i++) {
            ValidTimeDto currentDto = new ValidTimeDto();
            LocalDate today = LocalDate.now()
                    .plusDays(i);
            String todayDayName = today.getDayOfWeek()
                    .getDisplayName(TextStyle.SHORT, new Locale("ru"));

            for (WorkingHours currentHours : workingHours) {
                if (currentHours.getDayOfWeek().getTranslateLittle().equals(todayDayName)) {

                    currentDto.setMonthName(today.getMonth()
                            .getDisplayName(TextStyle.SHORT, new Locale("ru")));
                    currentDto.setDayName(todayDayName);
                    currentDto.setDayNumber(today.getDayOfMonth() + "");

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
