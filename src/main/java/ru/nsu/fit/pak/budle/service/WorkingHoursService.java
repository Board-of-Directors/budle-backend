package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.WorkingHoursDto;

import java.util.List;
import java.util.Set;

public interface WorkingHoursService {
    void saveWorkingHours(Set<WorkingHoursDto> workingHoursDto, Establishment establishment);

    List<ValidTimeDto> getValidBookingHoursByEstablishment(Establishment establishment);
}
