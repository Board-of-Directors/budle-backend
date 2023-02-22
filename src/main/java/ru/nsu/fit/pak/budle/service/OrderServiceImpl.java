package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.exceptions.NotEnoughRightsException;
import ru.nsu.fit.pak.budle.exceptions.OrderNotFoundException;
import ru.nsu.fit.pak.budle.mapper.OrderMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final EstablishmentRepository establishmentRepository;

    public void createOrder(OrderDto dto) {
        Order order = orderMapper.dtoToOrder(dto);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getOrders(Long id, Boolean byUser) {
        List<Order> orders = byUser ?
                orderRepository.findAllByUser(userRepository.getReferenceById(id)) :
                orderRepository.findAllByEstablishment(establishmentRepository.getReferenceById(id));
        return orders
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
    }

    @Override
    public void deleteOrder(Long orderId, Long id, Boolean byUser) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        if (byUser && order.getUser().getId().equals(id)) {
            order.setStatus(2);
        } else if (order.getEstablishment().getId().equals(id)) {
            order.setStatus(2);
        } else {
            throw new NotEnoughRightsException();
        }
    }

    @Override
    public void acceptOrder(Long orderId, Long establishmentId) {
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(orderId));
        order.setStatus(1);
        orderRepository.save(order);
    }

}
