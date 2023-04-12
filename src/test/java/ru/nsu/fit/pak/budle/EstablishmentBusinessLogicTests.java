package ru.nsu.fit.pak.budle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Pageable;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.EstablishmentService;

import javax.transaction.Transactional;
import java.util.Collections;


@SpringBootTest(classes = BudleApplication.class)
@Testcontainers
class EstablishmentBusinessLogicTests {

    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private UserRepository userRepository;

    private Establishment mainEstablishment;

    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongCategory() {
        insertEstablishments();
        Assertions.assertTrue(establishmentService.getEstablishmentByParams(
                Category.hotel.value,
                null,
                null,
                "",
                Pageable.ofSize(10)
        ).isEmpty());
    }

    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByRightCategory() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                null,
                null,
                null,
                "",
                Pageable.ofSize(10)
        ).size(), 1);
    }

    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongBooleanFlags() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                null,
                !mainEstablishment.getHasMap(),
                null,
                "",
                Pageable.ofSize(10)
        ).size(), 0);

        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                null,
                null,
                !mainEstablishment.getHasCardPayment(),
                "",
                Pageable.ofSize(10)
        ).size(), 0);
    }


    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByRightParams() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                null,
                mainEstablishment.getHasMap(),
                mainEstablishment.getHasCardPayment(),
                mainEstablishment.getName(),
                Pageable.ofSize(10)
        ).size(), 1);
    }


    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByWrongName() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                null,
                null,
                null,
                "Red babbit",
                Pageable.ofSize(10)
        ).size(), 0);
    }


    @Transactional
    public void insertEstablishments() {
        User ownerOfAllEstablishments = new User(0L, "Oleg", "+79993332211", "123456");
        userRepository.saveAndFlush(ownerOfAllEstablishments);

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
        establishmentRepository.saveAndFlush(mainEstablishment);
    }
}
