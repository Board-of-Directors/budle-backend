package ru.nsu.fit.pak.budle.service;

import org.springframework.data.domain.Pageable;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.*;

import java.util.List;
import java.util.Set;

/**
 * Service, that responsible for establishments.
 */

public interface EstablishmentService {

    /**
     * Function, that getting establishment by provided parameters.
     *
     * @param category       - in which category we search establishment.
     * @param hasMap         - does this establishment has a map.
     * @param hasCardPayment - does this establishment has a card payment.
     * @param name           - name/part of name os this establishment.
     * @param page           - page that we need.
     * @return pageable list of Establishment dto.
     */
    List<EstablishmentDto> getEstablishmentByParams(String category,
                                                    Boolean hasMap,
                                                    Boolean hasCardPayment,
                                                    String name,
                                                    Pageable page);

    /**
     * Function that creates establishment by provided parameters.
     *
     * @param dto dto with fields of new establishment model.
     */
    void createEstablishment(EstablishmentDto dto);

    /**
     * Function, that returns all categories from out system.
     *
     * @return all categories of establishment those we have.
     */
    List<String> getCategories();

    /**
     * Function, that returns all tags from our system.
     *
     * @return all tags of establishment those we have.
     */

    List<TagDto> getTags();

    /**
     * Function that add map of establishment to current establishment.
     *
     * @param establishmentId to what establishment we need to add map.
     * @param map             string representation of establishment map.
     */

    void addMap(Long establishmentId, String map);

    /**
     * Function, that getting all the photos of the provided establishment.
     *
     * @param establishmentId from what establishment we need to get photos.
     * @return set of all photos of the current establishment.
     */
    Set<PhotoDto> getPhotos(Long establishmentId);

    /**
     * Function, that compute and return all valid time for booking process.
     *
     * @param establishmentId for what establishment we need to compute valid booking time.
     * @return list of all valid booking times for a week.
     */
    List<ValidTimeDto> getValidTime(Long establishmentId);

    /**
     * Function, that returns all spot tags, those have current establishment.
     *
     * @param establishmentId from what establishment we get spot tags.
     * @return list of tag dto.
     */
    List<TagDto> getSpotTags(Long establishmentId);

    /**
     * Function, that returns establishment by provided id.
     * Or else throw establishment not found exception.
     *
     * @param establishmentId which we are searching for.
     * @return found establishment.
     */

    Establishment getEstablishmentById(Long establishmentId);

    List<ShortEstablishmentInfo> getEstablishmentsByOwner(Long id);
}
