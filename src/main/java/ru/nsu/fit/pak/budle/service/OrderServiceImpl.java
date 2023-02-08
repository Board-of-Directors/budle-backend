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
    public void deleteOrder(Long orderId, Long userId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Cannot find order"));
        if (order.getUser().getId().equals(userId)) {
            orderRepository.delete(order);
        } else {
            throw new NotEnoughRightsException("You cannot delete this order");
        }
    }

}
