package ru.nsu.fit.pak.budle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.dto.OrderDtoOutput;
import ru.nsu.fit.pak.budle.service.OrderService;

import javax.validation.Valid;
import java.util.List;

/**
 * Class, that represents order controller.
 */
@RestController
@RequestMapping(value = "order", produces = MediaType.APPLICATION_JSON_VALUE)
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    /**
     * Post request, that creates order for current establishment.
     *
     * @param dto - order dto represent order details, such as time, date, etc.
     *            For details of order dto you can watch OrderDTO class.
     */

    @PostMapping
    public void create(@RequestBody @Valid OrderDto dto) {
        orderService.createOrder(dto);

    }

    /**
     * Delete request, that must be sent by current user.
     * Delete his order from our database.
     *
     * @param orderId what order we need to delete.
     * @param userId  from what user we create request (will be deleted)
     */
    @DeleteMapping
    public void delete(@RequestParam Long orderId,
                       @RequestParam Long userId) {
        orderService.deleteOrder(orderId, userId, Boolean.TRUE);
    }

    /**
     * Get request, that return all orders of current user.
     *
     * @param userId - from what user we need to find orders.
     * @param status - with what status we need to find orders.
     * @return list of order dto.
     */
    @GetMapping
    public List<OrderDtoOutput> get(@RequestParam Long userId,
                                    @RequestParam(required = false) Integer status) {

        return orderService.getOrders(userId, Boolean.TRUE, status);
    }


}
