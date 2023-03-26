package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.dto.OrderDtoOutput;
import ru.nsu.fit.pak.budle.exceptions.*;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.SpotRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final EstablishmentRepository establishmentRepository;

    private final EstablishmentMapper establishmentMapper;

    private final SpotRepository spotRepository;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    public void createOrder(OrderDto dto) {
        logger.info("Creating order");
        logger.debug(dto.toString());
        Order order = modelMapper.map(dto, Order.class);
        User user = userRepository
                .findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        order.setUser(user);
        Establishment establishment = establishmentRepository
                .findById(dto.getEstablishmentId())
                .orElseThrow(() ->
                        new EstablishmentNotFoundException(dto.getEstablishmentId()));
        order.setEstablishment(establishment);
        if (establishment.getHasMap()) {
            Spot spot = spotRepository
                    .findById(dto.getSpotId())
                    .orElseThrow(IncorrectDataException::new);
            order.setSpot(spot);
        }
        orderRepository.save(order);
    }

    @Override
    public List<OrderDtoOutput> getOrders(Long id, Boolean byUser) {
        logger.info("Getting orders");
        logger.debug("byUser " + byUser + "\n"
                + "id " + id);
        List<Order> orders = byUser ?
                orderRepository.findAllByUser(userRepository.getReferenceById(id)) :
                orderRepository.findAllByEstablishment(establishmentRepository.getReferenceById(id));

        logger.debug("Result: " + orders);
        return orders
                .stream()
                .map(order -> {
                    Establishment establishmentSource = order.getEstablishment();
                    OrderDtoOutput orderDtoOutput = modelMapper.map(order, OrderDtoOutput.class);
                    orderDtoOutput.setEstablishment(establishmentMapper.modelToDto(establishmentSource));
                    return orderDtoOutput;
                })
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
