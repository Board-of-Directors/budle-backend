package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.EstablishmentListDto;
import ru.nsu.fit.pak.budle.dto.PhotoDto;
import ru.nsu.fit.pak.budle.dto.PhotoListDto;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.pak.budle.dto.response.ResponseOrderDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseSubcategoryDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseTagDto;
import ru.nsu.fit.pak.budle.dto.response.establishment.extended.ResponseExtendedEstablishmentInfo;
import ru.nsu.fit.pak.budle.service.EstablishmentServiceImpl;
import ru.nsu.fit.pak.budle.service.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;

/**
 * Class, that represents controller of requests, those connected with the establishment part.
 */
@RestController
@Validated
@RequestMapping(value = "establishment", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EstablishmentController {
    private final EstablishmentServiceImpl establishmentService;
    private final OrderService orderService;


    /**
     * Get requests for establishments.
     * Can filter establishments by fields, also implemented sorting and pagination.
     *
     * @param parameters - list of get parameters for establishments
     * @return list of establishment dto, list size included.
     */
    @GetMapping(value = "all")
    public EstablishmentListDto getEstablishments(@Valid RequestGetEstablishmentParameters parameters) {

        EstablishmentListDto list = new EstablishmentListDto();
        list.setEstablishments(
                establishmentService.getEstablishmentByParams(
                        parameters.category(),
                        parameters.hasMap(),
                        parameters.hasCardPayment(),
                        parameters.name(),
                        PageRequest.of(parameters.offset(), parameters.limit(),
                                Sort.by(parameters.sortValue())
                        )));
        list.setCount(list.getEstablishments().size());
        return list;
    }

    @GetMapping
    public ResponseExtendedEstablishmentInfo getEstablishment(@RequestParam Long establishmentId) {
        return establishmentService.getEstablishmentInfoById(establishmentId);
    }

    /**
     * Post request, that creating new establishment with provided fields.
     *
     * @param requestEstablishmentDto - representation of created establishment.
     *                                provides main information of this establishment,
     *                                such as names, description, etc.
     */
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEstablishment(@Valid @RequestBody RequestEstablishmentDto requestEstablishmentDto) {
        establishmentService.createEstablishment(requestEstablishmentDto);
    }

    /**
     * Get request, that returned all existing categories in our application.
     *
     * @return list of existed categories.
     */
    @GetMapping(value = "/category")
    public List<String> category() {
        return establishmentService.getCategories();
    }

    /**
     * Get request for orders, that connected with current establishment.
     *
     * @param establishmentId id of establishment, which orders we need to return.
     * @param status          status of orders, that we need to find.
     * @return list of orders dto.
     */

    @GetMapping(value = "/order")
    public List<ResponseOrderDto> orders(@RequestParam Long establishmentId,
                                         @RequestParam(required = false) Integer status) {
        return orderService.getOrders(null, establishmentId, status);
    }

    /**
     * Get request for tags, that returns all of existing tags in our system.
     *
     * @return list of tags dto.
     */
    @GetMapping(value = "/tags")
    public List<ResponseTagDto> tags() {
        return establishmentService.getTags();
    }

    /**
     * Put request, that accepted order by worker of establishment.
     *
     * @param establishmentId by what establishment we receive request (will be deleted)
     * @param orderId         - what order we need to accept.
     */
    @PutMapping(value = "/order/status")
    public void accept(@RequestParam Long establishmentId,
                       @RequestParam Long orderId,
                       @RequestParam Integer status) {
        orderService.setStatus(orderId, establishmentId, status);
    }


    @GetMapping(produces = MediaType.APPLICATION_XML_VALUE, value = "map")
    public String getMap(@RequestParam Long establishmentId) {
        return establishmentService.getMap(establishmentId);
    }


    /**
     * Get request, that searching for additional photos of establishment.
     *
     * @param establishmentId - from what establishment we get photos.
     * @return list of photos dto, included count of photos.
     */

    @GetMapping(value = "/images")
    public PhotoListDto getImages(@RequestParam Long establishmentId) {
        Set<PhotoDto> set = establishmentService.getPhotos(establishmentId);
        return new PhotoListDto(set, set.size());
    }

    /**
     * Get valid time for booking request of current establishment.
     *
     * @param establishmentId - from what establishment we need to get valid booking time.
     * @return list of valid time dto.
     */
    @GetMapping(value = "/time")
    public List<ValidTimeDto> getTime(@RequestParam Long establishmentId) {
        return establishmentService.getValidTime(establishmentId);
    }

    /**
     * Get request, that get all existing in our system spot-tags.
     * WARNING: spot-tags weak-connected with establishment-tags.
     *
     * @param establishmentId from what establishment we collect spot-tags.
     * @return list of spot tags.
     */
    @GetMapping(value = "/spotTags")
    public List<ResponseTagDto> getTags(@RequestParam Long establishmentId) {
        return establishmentService.getSpotTags(establishmentId);
    }

    @GetMapping(value = "/variants")
    public ResponseSubcategoryDto getCategoryVariants(@RequestParam String category) {
        return establishmentService.getCategoryVariants(category);

    }

    /**
     * Put request, that process map of establishment and put it to database.
     *
     * @param establishmentId - in what establishment we add map.
     * @param map             - svg document, that represents scheme of the establishment (with table and spots)
     */
    @PutMapping(value = "/map", consumes = "application/xml")
    public void createMap(@RequestParam Long establishmentId, @RequestBody String map) {
        establishmentService.addMap(establishmentId, map);
    }


}