package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.exceptions.NotEnoughRightsException;
import ru.nsu.fit.pak.budle.exceptions.OrderNotFoundException;
import ru.nsu.fit.pak.budle.mapper.OrderMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final EstablishmentRepository establishmentRepository;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void createOrder(OrderDto dto) {
        logger.info("Creating order");
        logger.debug(dto.toString());
        Order order = orderMapper.dtoToOrder(dto);
        orderRepository.save(order);
    }

    @Override
    public List<OrderDto> getOrders(Long id, Boolean byUser) {
        logger.info("Getting orders");
        logger.debug("byUser " + byUser + "\n"
                + "id " + id);
        List<Order> orders = byUser ?
                orderRepository.findAllByUser(userRepository.getReferenceById(id)) :
                orderRepository.findAllByEstablishment(establishmentRepository.getReferenceById(id));

        logger.debug("Result: " + orders);
        return orders
                .stream()
                .map(order -> modelMapper.map(order, OrderDto.class))
                .toList();
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId, Long id, Boolean byUser) {
        logger.info("Deleting order");
        logger.debug("OrderID " + orderId + "\n"
                + "id " + id);
        Order order = orderRepository
                .findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
        if (byUser && order.getUser().getId().equals(id)) {
            order.setStatus(2);
        } else if (order.getEstablishment().getId().equals(id)) {
            order.setStatus(2);
        } else {
            logger.warn("Not enough right for this operation");
            throw new NotEnoughRightsException();
        }
    }

    @Override
    @Transactional
    public void acceptOrder(Long orderId, Long establishmentId) {
        logger.info("Accepting order");
        Order order = orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(orderId));
        order.setStatus(1);
        orderRepository.save(order);
    }

}
