package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.OrderWithSpot;
import ru.nsu.fit.pak.budle.dao.Spot;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.dto.OrderDtoOutput;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.exceptions.*;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.SpotRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final UserRepository userRepository;

    private final EstablishmentService establishmentService;

    private final EstablishmentMapper establishmentMapper;

    private final SpotRepository spotRepository;

    private final WorkingHoursService workingHoursService;

    private final WorkingHoursMapper workingHoursMapper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void createOrder(OrderDto dto) {
        logger.info("Creating order");
        logger.debug(dto.toString());

        Establishment establishment = establishmentService
                .getEstablishmentById(dto.getEstablishmentId());
        Order order;
        if (dto.getSpotId() != null) {
            order = new OrderWithSpot();
            Spot spot = spotRepository
                    .findById(dto.getSpotId())
                    .orElseThrow(IncorrectDataException::new);
            ((OrderWithSpot) order).setSpot(spot);
        } else {
            order = new Order();
        }
        if (!bookingTimeIsValid(establishment, dto)) {
            throw new InvalidBookingTime();
        }
        order.setTime(dto.getTime());
        order.setGuestCount(dto.getGuestCount());
        order.setDate(Date.valueOf(dto.getDate()));
        User user = userRepository
                .findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        order.setUser(user);
        order.setEstablishment(establishment);
        orderRepository.save(order);
    }

    private boolean bookingTimeIsValid(Establishment establishment, OrderDto order) {
        List<ValidTimeDto> validTimeDtos =
                workingHoursService.getValidBookingHoursByEstablishment(establishment);
        ValidTimeDto orderTime = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(order.getDate());
        String bookingTime = order.getTime().toString().substring(0, order.getTime().toString().length() - 3);
        System.out.println(bookingTime);
        for (ValidTimeDto time : validTimeDtos) {
            if (Objects.equals(time.getDayName(), orderTime.getDayName()) &&
                    Objects.equals(time.getMonthName(), orderTime.getMonthName()) &&
                    Objects.equals(time.getDayNumber(), orderTime.getDayNumber()) &&
                    time.getTimes().contains(bookingTime)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public List<OrderDtoOutput> getOrders(Long id, Boolean byUser, Integer status) {
        logger.info("Getting orders");
        logger.debug("byUser " + byUser + "\n"
                + "id " + id);
        List<Order> orders = byUser ?
                orderRepository.findAllByUser(userRepository.getReferenceById(id)) :
                orderRepository.findAllByEstablishment(establishmentService.getEstablishmentById(id));

        logger.debug("Result: " + orders);


        return orders
                .stream()
                .map(order -> {
                    Establishment establishmentSource = order.getEstablishment();
                    OrderDtoOutput orderDtoOutput = modelMapper.map(order, OrderDtoOutput.class);
                    orderDtoOutput.setEstablishment(establishmentMapper.modelToDto(establishmentSource));
                    return orderDtoOutput;
                })
                .filter(order -> filterOrdersByStatus(order, status))
                .toList();
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId, Long id, Boolean byUser) {
        logger.info("Deleting order");
        logger.debug("OrderID " + orderId + "\n" + "id " + id);

        Order order = getOrderById(orderId);

        if (byUser && order.getUser().getId().equals(id)) {
            orderRepository.delete(order);
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
        Order order = getOrderById(orderId);
        order.setStatus(1);
        orderRepository.save(order);
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(orderId));
    }

    private boolean filterOrdersByStatus(OrderDtoOutput order, Integer status) {
        if (status == null) {
            return true;
        } else return order.getStatus().equals(status);
    }

}
