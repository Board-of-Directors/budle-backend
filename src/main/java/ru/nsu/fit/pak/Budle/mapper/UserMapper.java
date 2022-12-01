package ru.nsu.fit.pak.Budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.Budle.dao.Establishment;
import ru.nsu.fit.pak.Budle.dao.User;
import ru.nsu.fit.pak.Budle.dto.EstablishmentDto;
import ru.nsu.fit.pak.Budle.dto.UserDto;

import java.util.ArrayList;
import java.util.List;

@Component
public class UserMapper {
    public UserDto modelToDto(User user) {
        UserDto dto = new UserDto();
        dto.setId(user.getId());
        dto.setName(user.getName());
        dto.setPhoneNumber(user.getPhoneNumber());
        dto.setPassword(user.getPass());
        return dto;
    }

    public User dtoToUser(UserDto userDto) {
        User user = new User();
        user.setName(userDto.getName());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setPass(userDto.getPassword());
        return user;
    }

    public List<UserDto> modelListToDtoList(Iterable<User> userList) {
        List<UserDto> dtoList = new ArrayList<>();
        for (User user : userList) {
            dtoList.add(modelToDto(user));
        }
        return dtoList;
    }
}
