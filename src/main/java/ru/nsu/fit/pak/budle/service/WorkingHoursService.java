package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.WorkingHours;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestWorkingHoursDto;

import java.util.List;
import java.util.Set;

/**
 * Service that responsible for working hours.
 */
public interface WorkingHoursService {
    /**
     * Save provided working hours dto associated with current establishment.
     *
     * @param requestWorkingHoursDtos what we need to save.
     * @param establishment           with what working hours will be associated.
     */
    void saveWorkingHours(Set<RequestWorkingHoursDto> requestWorkingHoursDtos, Establishment establishment);

    /**
     * Function that computes and return valid booking time for establishment.
     *
     * @param establishment for what establishment we need to compute booking times.
     * @return list of valid times dto.
     */
    List<ValidTimeDto> getValidBookingHoursByEstablishment(Establishment establishment);

    /**
     * Удаление рабочих часов из базы данных.
     *
     * @param workingHours рабочие часы для удаления
     */
    void deleteHours(Set<WorkingHours> workingHours);
}
