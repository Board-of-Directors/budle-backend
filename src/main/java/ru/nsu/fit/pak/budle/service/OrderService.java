package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.OrderDto;

import java.util.List;

public interface OrderService {
    void createOrder(OrderDto dto);

    List<OrderDto> getOrders(Long userId);

    void deleteOrder(Long orderId, Long userId);
}
