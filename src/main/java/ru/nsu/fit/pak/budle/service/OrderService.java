package ru.nsu.fit.pak.budle.service;

import ru.nsu.fit.pak.budle.dto.OrderDto;

public interface OrderService {
    void createOrder(OrderDto dto);

    void getOrders(Long userId);

    void deleteOrder(Long orderId, Long userId);
}
