package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
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
@DisplayName("Тесты на пользовательскую бизнес-логику")
class UserBusinessLogicTests extends AbstractContextualTest {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CodeService codeService;

    @Autowired
    private CodeRepository codeRepository;

    @Test
    @DisplayName("Создание пользователя с существующим именем")
    public void testUserService_tryToRegisterUserWithExistingUsername() {
        RequestUserDto dto = new RequestUserDto("3111", "Oleg", "+79321312213");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));

    }

    @Test
    @DisplayName("Создание пользователя с существующим номером телефона")
    public void testUserService_tryToRegisterUserWithExistingNumber() {
        RequestUserDto dto = new RequestUserDto("3111", "Kerkey", "+7932131231");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));
    }

    @Test
    @DisplayName("Успешное создание и проверка кода доступа")
    public void testCreatingCodeForUser_AfterThatCheckingThisCode_MustBeTrue() {
        codeService.generateCode("+79833219999");
        Code code = codeRepository.findAll().get(0);
        Assertions.assertTrue(codeService.checkCode(code.getPhoneNumber(), code.getCode()));
    }

    @Test
    @DisplayName("Попытка проверить несуществующий код доступа")
    public void checkingNonExistedCode_MustThrownException() {
        Assertions.assertThrows(
            VerificationCodeWasFalseException.class,
            () -> codeService.checkCode("+79833211199", "1234")
        );
    }

    @Test
    @DisplayName("Попытка сгенерировать код с неверным номером телефона")
    public void tryToRequestWithIncorrectNumberFormat_MustBeAnException() {
        Assertions.assertThrows(
            IncorrectPhoneNumberFormatException.class,
            () -> codeService.generateCode("+7711")
        );
    }

    @Test
    @DisplayName("Попытка сгенерировать код для существующего в базе номера телефона")
    public void creatingCodeForExistedUser_MustBeException() {
        Assertions.assertThrows(
            UserAlreadyExistsException.class,
            () -> codeService.generateCode(userRepository.findAll().get(0).getPhoneNumber())
        );
    }

}
