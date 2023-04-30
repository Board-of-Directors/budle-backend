package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.*;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.dto.OrderDtoOutput;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.exceptions.*;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.SpotRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;

import javax.transaction.Transactional;
import java.sql.Date;
import java.sql.Time;
import java.time.temporal.ChronoUnit;
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

    private final EstablishmentRepository establishmentRepository;

    private final SpotRepository spotRepository;

    private final WorkingHoursService workingHoursService;

    private final WorkingHoursMapper workingHoursMapper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void createOrder(OrderDto dto) {
        logger.info("Creating order");
        logger.debug(dto.toString());
        Order order;
        if (dto.getSpotId() != null) {
            order = new OrderWithSpot();
            Spot spot = spotRepository
                    .findById(dto.getSpotId())
                    .orElseThrow(() -> new SpotNotFoundException(dto.getSpotId()));
            ((OrderWithSpot) order).setSpot(spot);
        } else {
            order = new Order();
        }

        Establishment establishment = establishmentService
                .getEstablishmentById(dto.getEstablishmentId());
        if (!bookingTimeIsValid(establishment, dto)) {
            throw new InvalidBookingTime();
        }
        order.setStartTime(dto.getTime());
        order.setEndTime(Time.valueOf(dto.getTime().toLocalTime().plus(order.getDuration(), ChronoUnit.MINUTES)));
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
        DayOfWeek dayOfWeek = DayOfWeek.getDayFromDayOfWeek(order.getDate().getDayOfWeek());

        List<ValidTimeDto> validTimeDtos =
                workingHoursService.getValidBookingHoursByEstablishment(establishment);
        ValidTimeDto orderTime = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(order.getDate());
        String bookingTime = order.getTime().toString().substring(0, order.getTime().toString().length() - 3);
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
    public List<OrderDtoOutput> getOrders(Long userId, Long establishmentId, Integer status) {
        logger.info("Getting orders");
        logger.debug("id " + userId);
        logger.debug("establishment " + establishmentId);

        User user = userId == null ?
                null : userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Establishment establishment = establishmentId == null ?
                null : establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Order> exampleQuery = Example.of(new Order(user, establishment, status), matcher);
        List<Order> orders = orderRepository.findAll(exampleQuery);

        logger.debug("Result: " + orders);

        return orders
                .stream()
                .map(order -> {
                    Establishment establishmentSource = order.getEstablishment();
                    OrderDtoOutput orderDtoOutput = modelMapper.map(order, OrderDtoOutput.class);
                    orderDtoOutput.setEstablishment(establishmentMapper.modelToDto(establishmentSource));
                    orderDtoOutput.setUserId(order.getUser().getId());
                    return orderDtoOutput;
                })
                .toList();
    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId, Long userId) {
        logger.info("Deleting order");
        logger.debug("OrderID " + orderId + "\n" + "id " + userId);

        Order order = getOrderById(orderId);

        if (order.getUser().getId().equals(userId)) {
            orderRepository.delete(order);
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

    @Override
    public void rejectOrder(Long orderId, Long establishmentId) {
        logger.info("Rejecting order");
        Order order = getOrderById(orderId);
        order.setStatus(2);
        orderRepository.save(order);
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(orderId));
    }

}
