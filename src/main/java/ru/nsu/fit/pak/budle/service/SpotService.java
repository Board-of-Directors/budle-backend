package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.SpotDto;
import ru.nsu.fit.pak.budle.dto.TimelineDto;

import java.util.List;

/**
 * Class that responsible for spots.
 */
public interface SpotService {
    /**
     * Function that gets spots by establishment.
     *
     * @param establishmentId which spots we need to find.
     * @return list of spot dto.
     */
    List<SpotDto> getSpotsByEstablishment(Long establishmentId);

    /**
     * Function that gets spot by provided id.
     *
     * @param spotId what we need to find.
     * @return spot dto.
     */
    SpotDto getSpotById(Long spotId);

    /**
     * Function that creates spot by local id in establishment and establishment id.
     *
     * @param localId         id of spot in current establishment.
     * @param establishmentId in what establishment we need to create new spot.
     */
    void createSpot(Long localId, Long establishmentId);

    /**
     * Получение временного промежутка для конкретного места.
     *
     * @param localId         идентификатор места для конкретного заведения
     * @param establishmentId идентификатор заведения
     * @return временной промежуток места
     */

    TimelineDto getSpotTimeline(Long localId, Long establishmentId);
}
