package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.fit.pak.budle.controller.UserController;
import ru.nsu.fit.pak.budle.dao.Code;
import ru.nsu.fit.pak.budle.dto.request.RequestUserDto;
import ru.nsu.fit.pak.budle.exceptions.IncorrectPhoneNumberFormatException;
import ru.nsu.fit.pak.budle.exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.budle.exceptions.VerificationCodeWasFalseException;
import ru.nsu.fit.pak.budle.repository.CodeRepository;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.CodeService;
import ru.nsu.fit.pak.budle.service.UserService;

@DatabaseSetup(value = "/user/before/users.xml")
class UserBusinessLogicTests extends AbstractContextualTest{

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private CodeRepository codeRepository;

    @Autowired
    private UserController userController;

    @Test
    public void testUserService_tryToRegisterUserWithExistingUsername() {
        RequestUserDto dto = new RequestUserDto("3111", "Oleg", "+79321312213");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));

    }

    @Test
    public void testUserService_tryToRegisterUserWithExistingNumber() {
        RequestUserDto dto = new RequestUserDto("3111", "Kerkey", "+7932131231");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));
    }

  /*  @Test
    public void testUserService_tryToRegisterUserWithDoesntExistingNumberAndUsername() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        request.setParameter("firstName", "Spring");
        request.setParameter("lastName", "Test");
        int oldSize = userRepository.findAll().size();
        RequestUserDto dto = new RequestUserDto("3111", "Kerkey", "+7932131239");
        userController.register(dto);
        Assertions.assertEquals(userRepository.findAll().size(), oldSize + 1);

        Assertions.assertTrue(userController.login(dto));

        // Assertions.assertEquals(securityService.findLoggedInUsername(),  dto.getUsername());
    }

   */

    @Test
    public void testCreatingCodeForUser_AfterThatCheckingThisCode_MustBeTrue() {
        codeService.generateCode("+79833219999");
        Code code = codeRepository.findAll().get(0);
        Assertions.assertTrue(codeService.checkCode(code.getPhoneNumber(), code.getCode()));
    }

    @Test
    public void checkingNonExistedCode_MustThrownException() {
        Assertions.assertThrows(
            VerificationCodeWasFalseException.class,
            () -> codeService.checkCode("+79833211199", "1234")
        );
    }

    @Test
    public void tryToRequestWithIncorrectNumberFormat_MustBeAnException() {
        Assertions.assertThrows(
            IncorrectPhoneNumberFormatException.class,
            () -> codeService.generateCode("+7711")
        );
    }

    @Test
    public void creatingCodeForExistedUser_MustBeException() {
        Assertions.assertThrows(
            UserAlreadyExistsException.class,
            () -> codeService.generateCode(userRepository.findAll().get(0).getPhoneNumber())
        );
    }

}
