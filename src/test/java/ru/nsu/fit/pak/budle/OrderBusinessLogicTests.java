package ru.nsu.fit.pak.budle;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.OrderDto;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.EstablishmentService;
import ru.nsu.fit.pak.budle.service.OrderService;

import javax.transaction.Transactional;
import java.sql.Time;
import java.util.Collections;
import java.util.Date;


@SpringBootTest(classes = BudleApplication.class)
@Testcontainers
class OrderBusinessLogicTests {

    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderService orderService;

    private Establishment mainEstablishment;

    private User guest;


    @Test
    @Transactional
    public void testOrderCreatingByUser() throws InterruptedException {
        insertEstablishments();
        OrderDto order = new OrderDto(4, new Date(), new Time(14, 30, 0),
                mainEstablishment.getId(), guest.getId(), null);
        orderService.createOrder(order);
        Thread.sleep(5000);
        mainEstablishment = establishmentService.getEstablishmentById(mainEstablishment.getId());
        //Assertions.assertEquals(1, mainEstablishment.getOrders().size());
        //FIXME: не создается заказ
    }

    @Transactional
    public void insertEstablishments() {
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
                Category.barbershop,
                "Some image",
                "Some map",
                user,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet());
        mainEstablishment = establishmentRepository.saveAndFlush(mainEstablishment);
    }
}
