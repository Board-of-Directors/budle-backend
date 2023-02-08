package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.mapper.OrderMapper;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final UserRepository userRepository;

    public void createOrder(OrderDto dto) {
        Order order = orderMapper.dtoToOrder(dto);
        orderRepository.save(order);
    }

    @Override
    public void getOrders(Long userId) {
        orderRepository.findAllByUser(userRepository.getReferenceById(userId));
    }

    @Override
    public void deleteOrder(Long orderId, Long userId) {
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new IllegalStateException("Cannot find order"));
        if (order.getUser().getId().equals(userId)) {
            orderRepository.delete(order);
        } else {
            throw new IllegalStateException("You cannot delete this order");
        }
    }

}
