package ru.nsu.fit.pak.budle.mapper;

import org.springframework.stereotype.Component;
import ru.nsu.fit.pak.budle.dao.User;
import ru.nsu.fit.pak.budle.dto.UserDto;

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
