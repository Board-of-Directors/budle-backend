package ru.nsu.fit.pak.budle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.UserDto;
import ru.nsu.fit.pak.budle.exceptions.UserAlreadyExistsException;
import ru.nsu.fit.pak.budle.repository.UserRepository;
import ru.nsu.fit.pak.budle.service.UserService;

import javax.transaction.Transactional;


@SpringBootTest(classes = BudleApplication.class)
@Testcontainers
class BudleApplicationTests {

    @Autowired
    private UserService userService;
    @Autowired
    private UserRepository userRepository;

    @Test
    @Transactional
    public void testUserService_tryToRegisterUserWithExistingUsername() {
        insertUsers();
        UserDto dto = new UserDto(4L, "3111", "Oleg", "+79321312213");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));
        System.out.println("TEST 1 WAS SUCCESS");

    }

    @Test
    @Transactional
    public void testUserService_tryToRegisterUserWithExistingNumber() {
        insertUsers();
        UserDto dto = new UserDto(4L, "3111", "Kerkey", "+7932131231");
        Assertions.assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(dto));
        System.out.println("TEST 2 WAS SUCCESS");
    }

    @Test
    @Transactional
    public void testUserService_tryToRegisterUserWithDoesntExistingNumberAndUsername() {
        insertUsers();
        int oldSize = userRepository.findAll().size();
        UserDto dto = new UserDto(4L, "3111", "Kerkey", "+7932131239");
        userService.registerUser(dto);
        Assertions.assertEquals(userRepository.findAll().size(), oldSize + 1);
    }


    private void insertUsers() {
        userRepository.save(new User(1L, "Oleg", "+7932131231", "3213"));
        userRepository.save(new User(2L, "Marvin", "+7932131233", "3213"));
        userRepository.save(new User(3L, "Sergey", "+7932131232", "3213"));
        userRepository.flush();
    }


}
