package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.DayOfWeek;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.OrderStatus;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseOrderDto;
import ru.nsu.fit.pak.budle.exceptions.*;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.mapper.OrderMapper;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.SpotRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.utils.OrderFactory;

import javax.transaction.Transactional;
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

    private final OrderFactory orderFactory;

    private final WorkingHoursService workingHoursService;

    private final WorkingHoursMapper workingHoursMapper;

    private final OrderMapper orderMapper;


    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void createOrder(RequestOrderDto dto) {
        logger.info("Creating order");
        logger.debug(dto.toString());
        Establishment establishment = establishmentService
                .getEstablishmentById(dto.getEstablishmentId());
        if (!bookingTimeIsValid(establishment, dto)) {
            throw new InvalidBookingTime();
        }

        Class<? extends Order> mappingClass = orderFactory.getEntity(dto);
        Order order = orderMapper.toEntity(dto, mappingClass);

        User user = userRepository
                .findById(dto.getUserId())
                .orElseThrow(UserNotFoundException::new);
        order.setUser(user);
        order.setEstablishment(establishment);
        orderRepository.save(order);
    }

    private boolean bookingTimeIsValid(Establishment establishment, RequestOrderDto order) {
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
    public List<ResponseOrderDto> getOrders(Long userId, Long establishmentId, Integer status) {
        logger.info("Getting orders");
        logger.debug("id " + userId);
        logger.debug("establishment " + establishmentId);

        User user = userId == null ?
                null : userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Establishment establishment = establishmentId == null ?
                null : establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));

        OrderStatus orderStatus = status == null ? null : OrderStatus.getStatusByInteger(status);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Order> exampleQuery = Example.of(new Order(user, establishment, orderStatus), matcher);
        List<Order> orders = orderRepository.findAll(exampleQuery);

        logger.debug("Result: " + orders);

        return orders
                .stream()
                .map(order -> {
                    Establishment establishmentSource = order.getEstablishment();
                    ResponseOrderDto responseOrderDto = modelMapper.map(order, ResponseOrderDto.class);
                    responseOrderDto.setEstablishment(establishmentMapper.toBasic(establishmentSource));
                    responseOrderDto.setUserId(order.getUser().getId());
                    return responseOrderDto;
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
    public void setStatus(Long orderId, Long establishmentId, Integer status) {
        logger.info("Setting order status");
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.getStatusByInteger(status));
        orderRepository.save(order);
    }


    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(orderId));
    }

}
