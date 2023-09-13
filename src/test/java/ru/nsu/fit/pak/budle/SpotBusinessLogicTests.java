package ru.nsu.fit.pak.budle;

import com.github.springtestdbunit.annotation.DatabaseSetup;
import org.junit.jupiter.api.DisplayName;
import org.springframework.beans.factory.annotation.Autowired;
import ru.nsu.fit.pak.budle.service.SpotService;

@DisplayName("Тесты на бизнес-функциональность работы с местами для бронирования")
@DatabaseSetup("/spots/before/prepare.xml")
public class SpotBusinessLogicTests extends AbstractContextualTest {
    @Autowired
    private SpotService spotService;
}
