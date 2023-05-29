package ru.nsu.fit.pak.budle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.support.TransactionTemplate;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.fit.pak.budle.controller.EstablishmentController;
import ru.nsu.fit.pak.budle.controller.OrderController;
import ru.nsu.fit.pak.budle.dao.*;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.request.RequestOrderDto;
import ru.nsu.fit.pak.budle.dto.response.ResponseOrderDto;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentNotFoundException;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.OrderRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.repository.WorkingHoursRepository;
import ru.nsu.fit.pak.budle.service.OrderService;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@SpringBootTest(classes = BudleApplication.class)
@Testcontainers
class OrderBusinessLogicTests {

    @Autowired
    private EstablishmentRepository establishmentRepository;
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

    @Autowired
    private WorkingHoursRepository workingHoursRepository;

    private Establishment mainEstablishment;

    @Autowired
    private TransactionTemplate transactionTemplate;

    private User guest;


    @Test
    public void testOrder_creatingRejectingAcceptingAndDeletingSequential() {
        insertEstablishments();
        long orderCount = orderRepository.findAll().size();
        RequestOrderDto order = new RequestOrderDto(4,
                LocalDate.now().plusDays(1),
                LocalTime.parse("14:30:00"),
                mainEstablishment.getId(),
                guest.getId(),
                null);
        orderController.create(order);
        Order createdOrder = orderRepository.findAll().get(0);
        System.out.println(createdOrder);
        Assertions.assertEquals(orderRepository.findAll().size(), orderCount + 1);
        establishmentController.accept(createdOrder.getId(), mainEstablishment.getId(), OrderStatus.REJECTED.getStatus());
        Assertions.assertEquals(createdOrder.getStatus(), OrderStatus.REJECTED);


        establishmentController.accept(mainEstablishment.getId(), createdOrder.getId(), OrderStatus.ACCEPTED.getStatus());
        Assertions.assertEquals(createdOrder.getStatus(), OrderStatus.ACCEPTED);

        List<ResponseOrderDto> listFromUser = orderController.get(guest.getId(), 1);
        List<ResponseOrderDto> listFromEstablishment = establishmentController.orders(mainEstablishment.getId(), 1);
        Assertions.assertEquals(listFromEstablishment, listFromUser);

        orderController.delete(createdOrder.getId(), guest.getId());
        Assertions.assertEquals(orderRepository.findAll().size(), orderCount);

    }


    @Test
    @Transactional
    public void tryToGetNonExistedOrder_mustBeThrownException() {
        Assertions.assertThrows(
                EstablishmentNotFoundException.class,
                () -> orderService.setStatus(111L, 22L, OrderStatus.ACCEPTED.getStatus()));
    }

    public void insertEstablishments() {
        transactionTemplate.execute((status) -> {
            User ownerOfAllEstablishments = new User(0L, "Oleg", "+79993332211", "123456");
            userRepository.saveAndFlush(ownerOfAllEstablishments);
            guest = new User(3L, "Varya", "+1111111111", "123456");
            guest = userRepository.saveAndFlush(guest);
            User user = userRepository.findAll().get(0);
            mainEstablishment = new Establishment(1L,
                    "Red Rabbit",
                    "Small bar",
                    "Koshurnikova st, 47",
                    false,
                    false,
                    4.9F,
                    400,
                    Category.game_club,
                    "Some image",
                    "Some map",
                    user,
                    Collections.emptySet(),
                    Collections.emptySet(),
                    Collections.emptySet(),
                    Collections.emptySet(),
                    Collections.emptySet());
            Set<WorkingHours> workingHours = new HashSet<>();
            for (int i = 0; i < DayOfWeek.values().length; i++) {
                new WorkingHours((long) i,
                        mainEstablishment,
                        LocalTime.parse("00:00:00"),
                        LocalTime.parse("23:59:59"),
                        DayOfWeek.values()[i]);
            }
            mainEstablishment = establishmentRepository.saveAndFlush(mainEstablishment);
            workingHoursRepository.saveAll(workingHours);
            return true;
        });
    }

}
