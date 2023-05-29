package ru.nsu.fit.pak.budle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.fit.pak.budle.controller.EstablishmentController;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.request.RequestEstablishmentDto;
import ru.nsu.fit.pak.budle.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.pak.budle.exceptions.EstablishmentAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.ImageSavingException;
import ru.nsu.fit.pak.budle.exceptions.IncorrectEstablishmentType;
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

    @Autowired
    private EstablishmentController establishmentController;

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private AuthenticationManager authenticationManager;

    private Establishment mainEstablishment;

    @Test
    @Transactional
    public void testCreatingEstablishment_creatingExistingEstablishment() {
        insertEstablishments();
        RequestEstablishmentDto creatingEstablishment = new RequestEstablishmentDto();
        creatingEstablishment.setName(mainEstablishment.getName());
        creatingEstablishment.setAddress(mainEstablishment.getAddress());
        creatingEstablishment.setImage(mainEstablishment.getImage());
        Assertions.assertThrows(EstablishmentAlreadyExistsException.class,
                () -> establishmentService.createEstablishment(creatingEstablishment));

        creatingEstablishment.setName("Hello world");
        Assertions.assertThrows(IncorrectEstablishmentType.class,
                () -> establishmentService.createEstablishment(creatingEstablishment));

        creatingEstablishment.setCategory("Рестораны");
        creatingEstablishment.setTags(Collections.emptySet());
        creatingEstablishment.setWorkingHours(Collections.emptySet());
        creatingEstablishment.setPhotosInput(Collections.emptySet());
        Assertions.assertThrows(ImageSavingException.class,
                () -> establishmentService.createEstablishment(creatingEstablishment));
    }

    @Test
    @Transactional
    public void testCategoryNumber() {
        Assertions.assertEquals(establishmentService.getCategories().size(), Category.values().length);
    }

    @Test
    @Transactional
    public void testTagsNumber() {
        Assertions.assertEquals(establishmentController.tags().size(), Tag.values().length);
    }

    @Test
    @Transactional
    public void testCreatingMap() {
        insertEstablishments();
        Establishment establishment = establishmentRepository.findAll().get(0);
        String addedMap = "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" fill=\"none\" height=\"555\" viewBox=\"0 0 375 555\" width=\"375\"/>";
        establishmentService.addMap(establishment.getId(), addedMap);
        //ResponseExtendedEstablishmentInfo dto = establishmentController.getEstablishment(establishment.getId());
        //Assertions.assertEquals(dto.getMap(), addedMap);
    }

    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongCategory() {
        insertEstablishments();
        Assertions.assertFalse(establishmentController.getEstablishments(new RequestGetEstablishmentParameters(null,
                null,
                null,
                null,
                null,
                null,
                null,
                null)
        ).getEstablishments().isEmpty());
    }

    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByRightCategory() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                new RequestGetEstablishmentParameters(null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        ).getCount(), 1);
    }

    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongBooleanFlags() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                new RequestGetEstablishmentParameters(null,
                        null,
                        false,
                        null,
                        null,
                        null,
                        null,
                        null)
        ).getCount(), 1);
    }


    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByRightParams() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                new RequestGetEstablishmentParameters(null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        ).getCount(), 1);
    }


    @Test
    @Transactional
    public void testCreatingEstablishment_FindEstablishmentByWrongName() {
        insertEstablishments();
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
                new RequestGetEstablishmentParameters(
                        "Red Rabbit",
                        null,
                        null,
                        null,
                        null,
                        null,
                        null,
                        null)
        ).getCount(), 1);
    }


    @Transactional
    public void insertEstablishments() {
        User ownerOfAllEstablishments = new User(1L, "Oleg", "+79993332211", "$2a$10$WkWmdPq4N/OEAdpWdQQ9b.g.JYX3PPSTs0cIWvolqasyZHrZkirnq");
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
                null,
                user,
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet(),
                Collections.emptySet());
        establishmentRepository.saveAndFlush(mainEstablishment);

        UserDetails userDetails = userDetailsService.loadUserByUsername("Oleg");
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken =
                new UsernamePasswordAuthenticationToken(userDetails, "111111111", userDetails.getAuthorities());


        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        SecurityContextHolder.getContext()
                .setAuthentication(authentication);
    }
}
