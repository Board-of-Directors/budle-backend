package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import ru.nsu.fit.pak.budle.controller.EstablishmentController;
import ru.nsu.fit.pak.budle.dao.Category;
import ru.nsu.fit.pak.budle.dao.Tag;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dao.establishment.Establishment;
import ru.nsu.fit.pak.budle.dto.request.RequestGetEstablishmentParameters;
import ru.nsu.fit.pak.budle.repository.EstablishmentRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.EstablishmentService;

@DatabaseSetup(value = "/establishment/before/establishment.xml")
class EstablishmentBusinessLogicTests extends AbstractContextualTest {

    private static final Long USER_ID = 200L;

    @BeforeEach
    public void makeMock() {
        User user = userRepository.findById(USER_ID).orElseThrow();
        mockUser(user);
    }

    @Autowired
    private EstablishmentService establishmentService;
    @Autowired
    private EstablishmentRepository establishmentRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private EstablishmentController establishmentController;

    @Test
    public void testCategoryNumber() {
        Assertions.assertEquals(establishmentService.getCategories().size(), Category.values().length);
    }

    @Test
    public void testTagsNumber() {
        Assertions.assertEquals(establishmentController.tags().size(), Tag.values().length);
    }

    @Test
    public void testCreatingMap() {
        Establishment establishment = establishmentRepository.findAll().get(0);
        String addedMap =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"no\"?><svg xmlns=\"http://www.w3.org/2000/svg\" fill=\"none\" height=\"555\" viewBox=\"0 0 375 555\" width=\"375\"/>";
        establishmentService.addMap(establishment.getId(), addedMap);
    }

    @Test
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongCategory() {
        Assertions.assertFalse(establishmentController.getEstablishments(
            RequestGetEstablishmentParameters.builder().build()
        ).getEstablishments().isEmpty());
    }

    @Test
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByRightCategory() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().build()
        ).getCount(), 1);
    }

    @Test
    public void testCreatingEstablishment_FindEstablishmentByParams_FindByWrongBooleanFlags() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().hasMap(false).build()
        ).getCount(), 1);
    }

    @Test
    public void testCreatingEstablishment_FindEstablishmentByRightParams() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().build()
        ).getCount(), 1);
    }

    @Test
    public void testCreatingEstablishment_FindEstablishmentByWrongName() {
        Assertions.assertEquals(establishmentService.getEstablishmentByParams(
            RequestGetEstablishmentParameters.builder().name("Red Rabbit").build()
        ).getCount(), 1);
    }

    private void mockUser(User user) {
        Authentication authentication = Mockito.mock(Authentication.class);
        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        Mockito.when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
    }
}
