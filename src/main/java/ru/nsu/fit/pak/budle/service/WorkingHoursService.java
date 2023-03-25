package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.DayOfWeek;
import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class WorkingHoursService {
    private final ModelMapper mapper;
    private final WorkingHoursRepository workingHoursRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

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
}
