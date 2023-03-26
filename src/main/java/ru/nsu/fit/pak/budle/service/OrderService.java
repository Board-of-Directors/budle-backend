package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.dto.OrderDtoOutput;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDto dto);

    List<OrderDtoOutput> getOrders(Long id, Boolean byUser, Integer status);

    void deleteOrder(Long orderId, Long id, Boolean byUser);

    void acceptOrder(Long orderId, Long establishmentId);
}
