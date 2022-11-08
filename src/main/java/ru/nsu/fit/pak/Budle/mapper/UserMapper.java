package ru.nsu.fit.pak.Budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.UserDto;

@Component
public class UserMapper {
    public UserDto modelToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setFirstName(user.getFirstName());
        dto.setLastName(user.getLastName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPassword(user.getPass());
        return dto;
    }

    public User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setFirstName(userDto.getFirstName());
        user.setLastName(userDto.getLastName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPass(userDto.getPassword());
        return user;
    }
}
