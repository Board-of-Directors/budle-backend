package ru.nsu.fit.pak.budle.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.OrderStatus;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.Worker;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.ValidTimeDto;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseOrderDto;
import ru.nsu.fit.pak.budle.exceptions.InvalidBookingTime;
import ru.nsu.fit.pak.budle.exceptions.NotEnoughRightsException;
import ru.nsu.fit.pak.budle.exceptions.OrderNotFoundException;
import ru.nsu.fit.pak.budle.mapper.OrderMapper;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.WorkerRepository;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final SecurityService securityService;
    private final EstablishmentService establishmentService;
    private final WorkingHoursService workingHoursService;
    private final WorkingHoursMapper workingHoursMapper;
    private final OrderMapper orderMapper;
    private final WorkerRepository workerRepository;

    @Override
    public void createOrder(RequestOrderDto dto) {
        log.info("Creating order {}", dto);
        Establishment establishment = establishmentService.getEstablishmentById(dto.getEstablishmentId());
        validateBookingTime(establishment, dto);
        Order order = orderMapper.toEntity(dto)
            .setUser(securityService.getLoggedInUser())
            .setEstablishment(establishment);
        orderRepository.save(order);
    }

    private void validateBookingTime(Establishment establishment, RequestOrderDto order) {
        if (!bookingTimeIsValid(establishment, order)) {
            log.warn("Booking time is not valid");
            throw new InvalidBookingTime();
        }
    }

    private boolean bookingTimeIsValid(Establishment establishment, RequestOrderDto order) {
        List<ValidTimeDto> validTimeDtos = workingHoursService.generateValidBookingHours(establishment);
        ValidTimeDto orderTime = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(order.getDate());
        return validTimeDtos.stream().anyMatch(time -> isTimesEquals(time, orderTime, order));
    }

    private boolean isTimesEquals(ValidTimeDto validTime, ValidTimeDto orderTime, RequestOrderDto order) {
        String bookingTime = order.getTime().toString();
        return Objects.equals(validTime.getDayName(), orderTime.getDayName())
            && Objects.equals(validTime.getMonthName(), orderTime.getMonthName())
            && Objects.equals(validTime.getDayNumber(), orderTime.getDayNumber())
            && validTime.getTimes().contains(bookingTime);

    }

    @Override
    public List<ResponseOrderDto> getUserOrders(Integer status) {
        log.info("Getting user orders");
        User user = securityService.getLoggedInUser();
        OrderStatus orderStatus = status == null ? null : OrderStatus.getStatusByInteger(status);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Order> exampleQuery = Example.of(new Order(user, orderStatus), matcher);
        List<Order> orders = orderRepository.findAll(exampleQuery);
        log.info("Result: " + orders);
        return orders.stream()
            .map(order -> orderMapper.toResponse(order, order.getEstablishment()))
            .toList();
    }

    @Override
    public List<ResponseOrderDto> getEstablishmentOrders(Long establishmentId, Integer status) {
        log.info("Get establishment orders");
        User user = securityService.getLoggedInUser();
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        userIsStuff(user, establishment);
        return orderRepository.findAllByEstablishment(establishment)
            .stream()
            .filter(order -> isNullOrEquals(status, order))
            .map(orderMapper::toResponse)
            .toList();

    }

    private boolean isNullOrEquals(Integer status, Order order) {
        if (status == null) {
            return true;
        } else {
            return Objects.equals(order.getStatus().getStatus(), status);
        }

    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId, Long userId) {
        Order order = getOrderById(orderId);
        User user = securityService.getLoggedInUser();
        log.info("Deleting order {} by user {}", orderId, user.getId());

        if (order.getUser().getId().equals(user.getId())) {
            orderRepository.delete(order);
        } else {
            log.warn("Not enough right for this operation");
            throw new NotEnoughRightsException();
        }
    }

    @Override
    @Transactional
    public void setStatus(Long orderId, Long establishmentId, Integer status) {
        log.info("Setting order status");
        Establishment establishment = establishmentService.getEstablishmentById(establishmentId);
        User user = securityService.getLoggedInUser();
        userIsStuff(user, establishment);
        orderRepository.save(getOrderById(orderId).setStatus(OrderStatus.getStatusByInteger(status)));
    }

    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId)
            .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private void userIsStuff(User user, Establishment establishment) {
        if (workerRepository.findByEstablishments(establishment)
            .stream()
            .map(Worker::getUser)
            .map(User::getId)
            .toList()
            .contains(user.getId()) ||
            Objects.equals(establishment.getOwner().getId(), user.getId())) {
        } else {
            throw new NotEnoughRightsException();
        }

    }

}
