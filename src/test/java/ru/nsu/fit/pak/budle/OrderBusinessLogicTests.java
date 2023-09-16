package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.controller.EstablishmentController;
import ru.nsu.fit.pak.budle.controller.OrderController;
import ru.nsu.fit.pak.budle.dao.*;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.exceptions.OrderNotFoundException;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.OrderService;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.stream.Stream;

@DisplayName("Тесты логики работы с заказами")
class OrderBusinessLogicTests extends AbstractContextualTest {

    private static final Long GUEST_ID = 3L;

    private static final Long OWNER_ID = 200L;

    private static final Long ORDER_ID = 250L;

    private static final Long ESTABLISHMENT_ID = 100L;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderController orderController;

    @Autowired
    private EstablishmentController establishmentController;

    @ParameterizedTest(name = TUPLE_PARAMETERIZED_DISPLAY_NAME)
    @MethodSource
    @DatabaseSetup(value = "/establishment/before/establishment_with_many_orders.xml")
    @DisplayName("Тест на получение заказов заведения")
    @SuppressWarnings("unused")
    public void getEstablishmentOrders(String name, Integer status, int size) {
        var answer = orderService.getEstablishmentOrders(ESTABLISHMENT_ID, status);
        Assertions.assertEquals(size, answer.size());
    }

    private static Stream<Arguments> getUserOrders() {
        return getEstablishmentOrders();
    }

    @Test
    @DatabaseSetup(value = "/establishment/before/establishment.xml")
    @DisplayName("Тест на успешное создание заказа")
    public void testOrder_creatingOrder() {
        User guest = userRepository.findById(GUEST_ID).orElseThrow();
        mockUser(guest);
        long orderCount = orderRepository.findAll().size();
        RequestOrderDto order = new RequestOrderDto(
            4,
            LocalDate.now().plusDays(1),
            LocalTime.parse("14:30:00"),
            ESTABLISHMENT_ID,
            guest.getId(),
            null
        );
        orderController.create(order);
        Assertions.assertEquals(orderCount + 1, orderRepository.findAll().size());
    }

    @Test
    @DatabaseSetup(value = "/establishment/before/establishment_with_order.xml")
    @DisplayName("Тест на успешное удаление заказа")
    public void successDeletingOrder() {
        User owner = userRepository.findById(OWNER_ID).orElseThrow();
        mockUser(owner);
        long orderCount = orderRepository.findAll().size();
        orderController.delete(
            ORDER_ID,
            GUEST_ID
        );
        Assertions.assertEquals(orderCount - 1, orderRepository.findAll().size());
    }

    @Test
    @DatabaseSetup(value = "/establishment/before/establishment_with_order.xml")
    @DisplayName("Тест на смену статуса заказа")
    public void testOrder_acceptingAndRejectingOrder() {
        User owner = userRepository.findById(OWNER_ID).orElseThrow();
        mockUser(owner);
        establishmentController.accept(
            ESTABLISHMENT_ID,
            ORDER_ID,
            OrderStatus.REJECTED.getStatus()
        );
        Order order = orderRepository.findAll().get(0);
        Assertions.assertEquals(order.getStatus(), OrderStatus.REJECTED);

        establishmentController.accept(
            ESTABLISHMENT_ID,
            ORDER_ID,
            OrderStatus.ACCEPTED.getStatus()
        );
        order = orderRepository.findAll().get(0);
        Assertions.assertEquals(order.getStatus(), OrderStatus.ACCEPTED);
    }

    @Test
    @DisplayName("Получение несуществующего заведения")
    public void tryToGetNonExistedOrder_mustBeThrownException() {
        Assertions.assertThrows(
            EstablishmentNotFoundException.class,
            () -> orderService.setStatus(111L, 22L, OrderStatus.ACCEPTED.getStatus())
        );
    }

    @Test
    @DisplayName("Получение несуществующего заказа")
    @DatabaseSetup(value = "/establishment/before/establishment_with_order.xml")
    public void getNonExistenceOrder() {
        Assertions.assertThrows(
            OrderNotFoundException.class,
            () -> orderService.setStatus(111L, ESTABLISHMENT_ID, OrderStatus.ACCEPTED.getStatus())
        );

    }

    public static Stream<Arguments> getEstablishmentOrders() {
        return Stream.of(
            Arguments.of("Получение всех заказов", null, 3),
            Arguments.of("Получение ожидающих заказов", OrderStatus.WAITING.getStatus(), 1),
            Arguments.of("Получение принятых заказов", OrderStatus.ACCEPTED.getStatus(), 1),
            Arguments.of("Получение отклоненных заказов", OrderStatus.REJECTED.getStatus(), 1)
        );
    }

    @ParameterizedTest(name = TUPLE_PARAMETERIZED_DISPLAY_NAME)
    @MethodSource
    @DatabaseSetup(value = "/establishment/before/establishment_with_many_orders.xml")
    @DisplayName("Тест на получение заказов человека")
    @SuppressWarnings("unused")
    public void getUserOrders(String name, Integer status, int size) {
        mockUser(userRepository.findById(GUEST_ID).orElseThrow());
        var answer = orderService.getUserOrders(status);
        Assertions.assertEquals(size, answer.size());
    }

}
