package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.EstablishmentListDto;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.pak.budle.dto.response.ResponseSubcategoryDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.extended.ResponseExtendedEstablishmentInfo;
import ru.nsu.fit.pak.budle.dto.response.establishment.shortInfo.ResponseShortEstablishmentInfo;

import java.util.List;
import java.util.Set;

/**
 * Service, that responsible for establishments.
 */

public interface EstablishmentService {

    /**
     * Function, that getting establishment by provided parameters.
     *
     * @param parameters - request parameters (pagination, sorting, additional)
     * @return pageable list of Establishment dto.
     */
    EstablishmentListDto getEstablishmentByParams(
            RequestGetEstablishmentParameters parameters
    );

    /**
     * Function that creates establishment by provided parameters.
     *
     * @param dto dto with fields of new establishment model.
     */
    void createEstablishment(RequestEstablishmentDto dto);

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

    List<ResponseTagDto> getTags();

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
    List<ResponseTagDto> getSpotTags(Long establishmentId);

    /**
     * Function, that returns establishment by provided id.
     * Or else throw establishment not found exception.
     *
     * @param establishmentId which we are searching for.
     * @return found establishment.
     */

    Establishment getEstablishmentById(Long establishmentId);

    ResponseExtendedEstablishmentInfo getEstablishmentInfoById(Long establishmentId);

    List<ResponseShortEstablishmentInfo> getEstablishmentsByOwner(Long id);

    ResponseSubcategoryDto getCategoryVariants(String category);

    String getMap(Long establishmentId);

    void updateEstablishment(Long establishmentId, RequestEstablishmentDto establishmentDto);

    void deleteEstablishment(Long establishmentId);
}
