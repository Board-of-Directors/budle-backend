package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
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
        orderRepository.findAll().get(0);
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

}
