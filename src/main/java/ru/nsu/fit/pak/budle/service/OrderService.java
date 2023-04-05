package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.dto.OrderDtoOutput;

import java.util.List;

/**
 * Service, that responsible for orders.
 */
public interface OrderService {
    /**
     * Creating new order by provided order dto.
     *
     * @param dto contains information about new order.
     */
    void createOrder(OrderDto dto);

    /**
     * Getting all the orders with provided parameters.
     *
     * @param id     of provided entity.
     * @param byUser indicates that function must associate id with user, or,
     *               if false - with establishment.
     * @param status - with what status we're searching orders.
     * @return list of order dto.
     */

    List<OrderDtoOutput> getOrders(Long id, Boolean byUser, Integer status);

    /**
     * @param orderId what order we need to delete.
     * @param id      of provided entity.
     * @param byUser  indicates that function must associate id with user, or,
     *                if false - with establishment.
     */
    void deleteOrder(Long orderId, Long id, Boolean byUser);

    /**
     * @param orderId         what order we need to accept.
     * @param establishmentId from what establishment was this request.
     */
    void acceptOrder(Long orderId, Long establishmentId);
}
