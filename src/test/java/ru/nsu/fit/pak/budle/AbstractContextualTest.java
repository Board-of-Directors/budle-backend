package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.DbUnitTestExecutionListener;
import com.github.springtestdbunit.TransactionDbUnitTestExecutionListener;
import com.github.springtestdbunit.annotation.DatabaseTearDown;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.nsu.fit.pak.budle.dao.User;

import static org.junit.jupiter.params.ParameterizedTest.INDEX_PLACEHOLDER;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = BudleApplication.class)
@TestExecutionListeners({
    DependencyInjectionTestExecutionListener.class,
    DbUnitTestExecutionListener.class,
    TransactionDbUnitTestExecutionListener.class,
})
@RunWith(MockitoJUnitRunner.class)
@Testcontainers
@DatabaseTearDown(value = "/empty.xml")
public class AbstractContextualTest {
    protected static final String TUPLE_PARAMETERIZED_DISPLAY_NAME = "[" + INDEX_PLACEHOLDER + "] {0}";

    protected void mockUser(User user) {
        Authentication authentication = mock(Authentication.class);
        SecurityContext securityContext = mock(SecurityContext.class);
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getPrincipal()).thenReturn(user);
        SecurityContextHolder.setContext(securityContext);
    }
}
