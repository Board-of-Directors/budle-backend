package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.OrderDto;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDto dto);

    List<OrderDto> getOrders(Long id, Boolean byUser);

    void deleteOrder(Long orderId, Long userId);
}
