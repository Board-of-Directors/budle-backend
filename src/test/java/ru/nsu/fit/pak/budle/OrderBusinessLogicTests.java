package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.ExpectedDatabase;
import com.github.springtestdbunit.assertion.DatabaseAssertionMode;
import org.flywaydb.test.annotation.FlywayTest;
import org.flywaydb.test.dbunit.DBUnitSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.MockedStatic;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.controller.EstablishmentController;
import ru.nsu.fit.pak.budle.controller.OrderController;
import ru.nsu.fit.pak.budle.dao.Order;
import ru.nsu.fit.pak.budle.dao.OrderStatus;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.exceptions.InvalidBookingTime;
import ru.nsu.fit.pak.budle.exceptions.NotEnoughRightsException;
import ru.nsu.fit.pak.budle.exceptions.OrderNotFoundException;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.OrderService;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.stream.Stream;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mockStatic;

@DisplayName("Тесты логики работы с заказами")
@FlywayTest
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
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_many_orders.xml"})
    @DisplayName("Тест на получение заказов заведения")
    @SuppressWarnings("unused")
    public void getEstablishmentOrders(String name, Integer status, int size) {
        var answer = orderService.getEstablishmentOrders(ESTABLISHMENT_ID, status);
        Assertions.assertEquals(size, answer.size());
    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment.xml"})
    @DisplayName("Тест на успешное создание заказа")
    public void testOrder_creatingOrder() {
        String instantExpected = "2014-12-23T00:01:00Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        LocalDate now = LocalDate.now(clock);
        LocalDate bookDate = LocalDate.of(2014, 12, 23);
        ZonedDateTime zonedNow = ZonedDateTime.now(clock);
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class);
             MockedStatic<ZonedDateTime> mockedZonedTime = mockStatic(ZonedDateTime.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(now);
            mockedZonedTime.when(() -> ZonedDateTime.now(any(ZoneId.class))).thenReturn(zonedNow);
            User guest = userRepository.findById(GUEST_ID).orElseThrow();
            mockUser(guest);
            long orderCount = orderRepository.findAll().size();
            RequestOrderDto order = new RequestOrderDto(
                4,
                bookDate,
                LocalTime.parse("00:30:00"),
                ESTABLISHMENT_ID,
                guest.getId(),
                null
            );
            orderController.create(order);
            Assertions.assertEquals(orderCount + 1, orderRepository.findAll().size());
        }
    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_spots.xml"})
    @ExpectedDatabase(
        value = "/orders/after/order_with_spot.xml",
        assertionMode = DatabaseAssertionMode.NON_STRICT_UNORDERED
    )
    @DisplayName("Тест на успешное создание заказа с местом")
    public void creatingOrderWithSpot() {
        String instantExpected = "2014-12-23T00:01:00Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        LocalDate now = LocalDate.now(clock);
        LocalDate bookDate = LocalDate.of(2014, 12, 23);
        ZonedDateTime zonedNow = ZonedDateTime.now(clock);
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class);
             MockedStatic<ZonedDateTime> mockedZonedTime = mockStatic(ZonedDateTime.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(now);
            mockedZonedTime.when(() -> ZonedDateTime.now(any(ZoneId.class))).thenReturn(zonedNow);
            User guest = userRepository.findById(GUEST_ID).orElseThrow();
            mockUser(guest);
            long orderCount = orderRepository.findAll().size();
            RequestOrderDto order = new RequestOrderDto(
                4,
                bookDate,
                LocalTime.parse("00:30:00"),
                ESTABLISHMENT_ID,
                guest.getId(),
                1L
            );
            orderController.create(order);
            Assertions.assertEquals(orderCount + 1, orderRepository.findAll().size());
        }
    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_spots.xml"})
    @DisplayName("Тест на создание заказа с местом в невалидное время")
    public void creatingNotValidTimeOrder() {
        String instantExpected = "2014-12-23T00:01:00Z";
        Clock clock = Clock.fixed(Instant.parse(instantExpected), ZoneId.of("UTC"));
        LocalDate now = LocalDate.now(clock);
        LocalDate bookDate = LocalDate.of(2014, 12, 23);
        ZonedDateTime zonedNow = ZonedDateTime.now(clock);
        try (MockedStatic<LocalDate> mockedStatic = mockStatic(LocalDate.class);
             MockedStatic<ZonedDateTime> mockedZonedTime = mockStatic(ZonedDateTime.class)) {
            mockedStatic.when(LocalDate::now).thenReturn(now);
            mockedZonedTime.when(() -> ZonedDateTime.now(any(ZoneId.class))).thenReturn(zonedNow);
            User guest = userRepository.findById(GUEST_ID).orElseThrow();
            mockUser(guest);
            long orderCount = orderRepository.findAll().size();
            RequestOrderDto order = new RequestOrderDto(
                4,
                bookDate,
                LocalTime.parse("23:30:00"),
                ESTABLISHMENT_ID,
                guest.getId(),
                1L
            );
            Assertions.assertThrows(InvalidBookingTime.class, () -> orderController.create(order));
            Assertions.assertEquals(orderCount, orderRepository.findAll().size());
        }
    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_order.xml"})
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
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_order.xml"})
    @DisplayName("Тест на исключение при удалении заказа")
    public void exceptionDeletingOrder() {
        User owner = userRepository.findById(OWNER_ID).orElseThrow();
        mockUser(owner);
        long orderCount = orderRepository.findAll().size();
        Assertions.assertThrows(
            NotEnoughRightsException.class,
            () -> orderController.delete(ORDER_ID, OWNER_ID)
        );
        Assertions.assertEquals(orderCount, orderRepository.findAll().size());
    }

    @Test
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_order.xml"})
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
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_order.xml"})
    public void getNonExistenceOrder() {
        Assertions.assertThrows(
            OrderNotFoundException.class,
            () -> orderService.setStatus(111L, ESTABLISHMENT_ID, OrderStatus.ACCEPTED.getStatus())
        );

    }

    private static Stream<Arguments> getUserOrders() {
        return getEstablishmentOrders();
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
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_many_orders.xml"})
    @DisplayName("Тест на получение заказов человека")
    @SuppressWarnings("unused")
    public void getUserOrders(String name, Integer status, int size) {
        mockUser(userRepository.findById(GUEST_ID).orElseThrow());
        var answer = orderService.getUserOrders(status);
        Assertions.assertEquals(size, answer.size());
    }

    @Test
    @DisplayName("Получение заказов несуществующего заведения")
    @FlywayTest
    @DBUnitSupport(loadFilesForRun = {"CLEAN_INSERT", "/establishment/before/establishment_with_order.xml"})
    public void getOrderNonExistenceEstablishment() {
        Assertions.assertThrows(
            EstablishmentNotFoundException.class,
            () -> orderService.getEstablishmentOrders(123L, 0)
        );
    }

}
