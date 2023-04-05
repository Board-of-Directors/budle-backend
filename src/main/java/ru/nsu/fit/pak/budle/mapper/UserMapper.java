package ru.nsu.fit.pak.budle.mapper;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.UserDto;

/**
 * Class, that represent mapper for user class.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {
    private final ModelMapper modelMapper;

    /**
     * Convert user dto object to user model object.
     *
     * @param userDto object.
     * @return user model object.
     */

    public User dtoToModel(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }
}
