package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.repository.OrderRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final ModelMapper modelMapper;

    public void createOrder(OrderDto dto) {
        Order order = modelMapper.map(dto, Order.class);
        orderRepository.save(order);

    }

}
