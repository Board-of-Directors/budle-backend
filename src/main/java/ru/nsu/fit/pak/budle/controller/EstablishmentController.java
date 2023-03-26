package ru.nsu.fit.pak.budle.controller;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.*;
import ru.nsu.fit.pak.budle.service.EstablishmentServiceImpl;
import ru.nsu.fit.pak.budle.service.OrderService;

import javax.validation.Valid;
import java.util.List;
import java.util.Set;


@RestController
@RequestMapping(value = "establishment", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor(access = AccessLevel.PUBLIC)
public class EstablishmentController {
    private final EstablishmentServiceImpl establishmentService;
    private final OrderService orderService;
    

    @GetMapping
    public EstablishmentListDto getEstablishments(@RequestParam(required = false, defaultValue = "") String name, @RequestParam(required = false) String category, @RequestParam(required = false) Boolean hasMap, @RequestParam(required = false) Boolean hasCardPayment, @RequestParam(required = false, defaultValue = "0") Integer offset, @RequestParam(required = false, defaultValue = "100") Integer limit, @RequestParam(required = false, defaultValue = "name") String sortValue) {
        EstablishmentListDto list = new EstablishmentListDto();
        list.setEstablishments(establishmentService.getEstablishmentByParams(category, hasMap, hasCardPayment, name, PageRequest.of(offset, limit, Sort.by(sortValue))));
        list.setCount(list.getEstablishments().size());
        return list;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public void createEstablishment(@Valid @RequestBody EstablishmentDto establishmentDto) {
        establishmentService.createEstablishment(establishmentDto);
    }

    @GetMapping(value = "/category")
    public List<String> category() {
        return establishmentService.getCategories();
    }


    @GetMapping(value = "/order")
    public List<OrderDtoOutput> orders(@RequestParam Long establishmentId,
                                       @RequestParam(required = false) Integer status) {
        return orderService.getOrders(establishmentId, Boolean.FALSE, status);
    }

    @GetMapping(value = "/tags")
    public List<TagDto> tags() {
        return establishmentService.getTags();
    }

    @PutMapping(value = "/order")
    public void accept(@RequestParam Long establishmentId, @RequestParam Long orderId) {
        orderService.acceptOrder(orderId, establishmentId);
    }

    @DeleteMapping
    public void deleteOrder(@RequestParam Long orderId, @RequestParam Long establishmentId) {
        orderService.deleteOrder(orderId, establishmentId, Boolean.FALSE);
    }

    @GetMapping(value = "/images")
    public PhotoListDto getImages(@RequestParam Long establishmentId) {
        Set<PhotoDto> set = establishmentService.getPhotos(establishmentId);
        return new PhotoListDto(set, set.size());
    }

    @PutMapping(value = "/map", consumes = "application/xml")
    public void createMap(@RequestParam Long establishmentId, @RequestBody String map) {
        establishmentService.addMap(establishmentId, map);
    }


}