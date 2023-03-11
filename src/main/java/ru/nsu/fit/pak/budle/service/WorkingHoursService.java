package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
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

    public void saveWorkingHours(Set<WorkingHoursDto> workingHoursDto, Establishment establishment) {
        for (WorkingHoursDto dto : workingHoursDto) {
            WorkingHours workingHours = mapper.map(dto, WorkingHours.class);
            workingHours.setEstablishment(establishment);
            workingHoursRepository.save(workingHours);
        }
    }
}
