package ru.nsu.fit.pak.budle.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.exceptions.InvalidBookingTime;
import ru.nsu.fit.pak.budle.exceptions.NotEnoughRightsException;
import ru.nsu.fit.pak.budle.exceptions.OrderNotFoundException;
import ru.nsu.fit.pak.budle.mapper.EstablishmentMapper;
import ru.nsu.fit.pak.budle.mapper.OrderMapper;
import ru.nsu.fit.pak.budle.mapper.WorkingHoursMapper;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.utils.OrderFactory;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;

    private final ModelMapper modelMapper;

    private final SecurityService securityService;

    private final EstablishmentService establishmentService;

    private final EstablishmentMapper establishmentMapper;

    private final EstablishmentRepository establishmentRepository;


    private final OrderFactory orderFactory;

    private final WorkingHoursService workingHoursService;

    private final WorkingHoursMapper workingHoursMapper;

    private final OrderMapper orderMapper;


    @Override
    public void createOrder(RequestOrderDto dto) {
        log.info("Creating order");
        log.info(dto.toString());
        Establishment establishment = establishmentService
                .getEstablishmentById(dto.getEstablishmentId());
        if (!bookingTimeIsValid(establishment, dto)) {
            log.warn("Booking time is not valid");
            throw new InvalidBookingTime();
        }

        Class<? extends Order> mappingClass = orderFactory.getEntity(dto);
        Order order = orderMapper.toEntity(dto, mappingClass);

        User user = securityService.getLoggedInUser();
        order.setUser(user);
        order.setEstablishment(establishment);
        orderRepository.save(order);
    }

    private boolean bookingTimeIsValid(Establishment establishment, RequestOrderDto order) {
        List<ValidTimeDto> validTimeDtos =
                workingHoursService.getValidBookingHoursByEstablishment(establishment);
        ValidTimeDto orderTime = workingHoursMapper.convertFromDateAndTimeToValidTimeDto(order.getDate());
        String bookingTime = order.getTime().toString();
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
    public List<ResponseOrderDto> getUserOrders(Integer status) {
        log.info("Getting user orders");
        User user = securityService.getLoggedInUser();

        OrderStatus orderStatus = status == null ? null : OrderStatus.getStatusByInteger(status);
        ExampleMatcher matcher = ExampleMatcher.matching().withIgnoreNullValues();
        Example<Order> exampleQuery = Example.of(new Order(user, orderStatus), matcher);
        List<Order> orders = orderRepository.findAll(exampleQuery);

        log.info("Result: " + orders);

        return orders
                .stream()
                .map(order -> {
                    Establishment establishmentSource = order.getEstablishment();
                    ResponseOrderDto responseOrderDto = modelMapper.map(order, ResponseOrderDto.class);
                    responseOrderDto.setEstablishment(establishmentMapper.toBasic(establishmentSource));
                    responseOrderDto.setUsername(order.getUser().getUsername());
                    return responseOrderDto;
                })
                .toList();
    }

    @Override
    public List<ResponseOrderDto> getEstablishmentOrders(Long establishmentId, Integer status) {
        log.info("Get establishment orders");
        User user = securityService.getLoggedInUser();
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));
        userIsStuff(user, establishment);

        return establishment
                .getOrders()
                .stream()
                .map(order -> {
                            ResponseOrderDto responseOrderDto = modelMapper.map(order, ResponseOrderDto.class);
                            responseOrderDto.setUsername(order.getUser().getUsername());
                            return responseOrderDto;
                        }
                )
                .toList();


    }

    @Override
    @Transactional
    public void deleteOrder(Long orderId, Long userId) {
        log.info("Deleting order");
        log.info("OrderID " + orderId + "\n" + "id " + userId);

        Order order = getOrderById(orderId);

        if (order.getUser().getId().equals(userId)) {
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
        Establishment establishment = establishmentRepository.findById(establishmentId)
                .orElseThrow(() -> new EstablishmentNotFoundException(establishmentId));
        User user = securityService.getLoggedInUser();
        userIsStuff(user, establishment);
        Order order = getOrderById(orderId);
        order.setStatus(OrderStatus.getStatusByInteger(status));
        orderRepository.save(order);
    }


    private Order getOrderById(Long orderId) {
        return orderRepository.findById(orderId).orElseThrow(() ->
                new OrderNotFoundException(orderId));
    }

    private void userIsStuff(User user, Establishment establishment) {
        if (establishment.getWorkers()
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
